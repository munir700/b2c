package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import timber.log.Timber

class PhoneContactViewModel(application: Application) :
    Y2YBaseViewModel<IPhoneContact.State>(application),
    IPhoneContact.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IPhoneContact.State = PhoneContactState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    private var pagingState: MutableLiveData<PagingState> = MutableLiveData()
    override var phoneContactLiveData: MutableLiveData<List<Contact>> = MutableLiveData()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getState(): LiveData<PagingState> {
        return pagingState
    }

    override fun listIsEmpty(): Boolean {
        return phoneContactLiveData.value?.isEmpty() ?: true
    }

    private suspend fun getLocalContacts() = viewModelBGScope.async(Dispatchers.IO) {
        fetchContacts(context)
    }.await()

    override fun onCleared() {
        viewModelBGScope.close()
        super.onCleared()
    }

    override fun getY2YBeneficiaries() {

        pagingState.value = PagingState.LOADING
        launch {
            val localContacts = getLocalContacts()
            if (localContacts.isEmpty()) {
                phoneContactLiveData.value = mutableListOf()
                pagingState.value = PagingState.DONE
            } else {
                when (val response = repository.getY2YBeneficiaries(localContacts)) {
                    is RetroApiResponse.Success -> {
                        phoneContactLiveData.value = response.data.data
                        pagingState.value = PagingState.DONE
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                        pagingState.value = PagingState.ERROR
                    }
                }
            }
        }
    }

    /*
      * Defines an array that contains column names to move from
      * the Cursor to the ListView.
      */
    @SuppressLint("InlinedApi", "ObsoleteSdkInt")
    private val FROM_COLUMNS: Array<String> = arrayOf(
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        } else {
            ContactsContract.Contacts.DISPLAY_NAME
        }
    )

    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.LOOKUP_KEY,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    )

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private val SELECTION: String =
        "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE ?"


    private fun fetchContacts(context: Context): MutableList<Contact> {

        val contacts: MutableList<Contact> = ArrayList()
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val defaultCountryCode = Utils.getDefaultCountryCode(context)

            val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, SELECTION, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )
            try {
                cursor?.let {
                    if (it.count > 0) {
                        while (it.moveToNext()) {
                            if (viewModelBGScope.isActive) {
                                val name =
                                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                                val phoneWihtoutCountryCode =
                                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                val phoneNo = Utils.getPhoneWithoutCountryCode(
                                    defaultCountryCode,
                                    phoneWihtoutCountryCode
                                )

                                val countryCode =
                                    Utils.getPhoneNumberCountryCodeForAPI(
                                        defaultCountryCode,
                                        phoneWihtoutCountryCode
                                    )

                                val contactId =
                                    it.getLong(it.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))

                                val contactId2 =
                                    it.getLong(it.getColumnIndex(ContactsContract.Data.CONTACT_ID))

                                val email = fetchContactsEmail(contactId)

                                var photoContentUri: Uri? = getPhotoUri(contactId2)
                                if (photoContentUri == null) photoContentUri = Uri.EMPTY

                                Timber.d("contacts: $name $phoneNo $email $countryCode $photoContentUri")

                                val contact = Contact(
                                    name,
                                    countryCode,
                                    phoneNo,
                                    email,
                                    photoContentUri?.toString(),
                                    false, null
                                )
                                contacts.add(contact)
                            }
                        }
                    }
                }
                cursor?.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return contacts
    }

    private fun getPhotoUri(contactId: Long): Uri? {
        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI, null,
            ContactsContract.Data.CONTACT_ID
                    + "="
                    + contactId
                    + " AND "

                    + ContactsContract.Data.MIMETYPE
                    + "='"
                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                    + "'", null, null
        )
        try {
            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    cursor.close()
                    return null // no photo
                }
            } else {
                return null // error in cursor process
            }

        } catch (e: Exception) {
            cursor?.close()
            e.printStackTrace()
            return null
        }
        val person = ContentUris.withAppendedId(
            ContactsContract.Contacts.CONTENT_URI, contactId
        )
        return Uri.withAppendedPath(
            person,
            ContactsContract.Contacts.Photo.CONTENT_DIRECTORY
        )
    }

    private fun fetchContactsEmail(id: Long): String {
        var emlAdd = ""
        val PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Email.CONTACT_ID,
            ContactsContract.CommonDataKinds.Email.DATA
        )
        val order = ("CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE")

        val filter =
            ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE '' AND " + ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + (id)
        val cur = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            PROJECTION,
            filter,
            null,
            order
        )
        if (cur!!.moveToFirst()) {
            do {
                emlAdd = cur.getString(1)
            } while (cur.moveToNext())
        }

        cur.close()
        return emlAdd
    }
}