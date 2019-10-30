package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
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

    override fun getY2YBeneficiaries() {
        launch {
            state.loading = true
            val response = fetchContacts(context)
            when (val response = repository.getY2YBeneficiaries(response)) {
                is RetroApiResponse.Success -> {
                    phoneContactLiveData.value = response.data.data
                    pagingState.value = PagingState.DONE
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    pagingState.value = PagingState.ERROR
                }
            }
            state.loading = false
        }
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

    private fun fetchContacts(context: Context): MutableList<Contact> {
        val defaultCountryCode = Utils.getDefaultCountryCode(context)
        val contacts: MutableList<Contact> = ArrayList()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        try {

            if ((cursor?.count ?: 0) > 0) {
                while (cursor!!.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val phoneWihtoutCountryCode =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

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
                        cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))
                    val contactId2 =
                        cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))
                    val email = fetchContactsEmail(contactId)

                    var photoContentUri: Uri? = getPhotoUri(contactId2)
                    if (photoContentUri == null) photoContentUri = Uri.EMPTY

                    Log.d(
                        "contact",
                        "getAllContacts: $name $phoneNo $email $countryCode  ${photoContentUri}"
                    )
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
            cursor?.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return contacts
    }

}