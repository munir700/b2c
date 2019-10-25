package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.paging

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ContactsDataSource(
    private val context: Context,
    private val repo: CustomersRepository
) :
    PageKeyedDataSource<Long, Contact>() {
    var state: MutableLiveData<PagingState> = MutableLiveData()

    fun retry() {

    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Contact>
    ) {
        updateState(PagingState.LOADING)

        GlobalScope.launch {
            val response = fetchContacts(context)
            if (response.isNotEmpty()) {
                when (val response =
                    repo.getY2YBeneficiaries(response)) {
                    is RetroApiResponse.Success -> {
                        callback.onResult(
                            response.data.data,
                            null,
                            null
                        )
                        updateState(PagingState.DONE)
                    }
                    is RetroApiResponse.Error -> {
                        callback.onResult(
                            listOf(),
                            null,
                            null
                        )
                        updateState(PagingState.ERROR)
                    }
                }
            } else {
                callback.onResult(
                    listOf(),
                    null,
                    null
                )
                updateState(PagingState.ERROR)
            }
        }
    }

    private fun fetchContactsEmail() {
        val PROJECTION = arrayOf(
            ContactsContract.RawContacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val order = ("CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE")
        val filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''"
        val cur = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            PROJECTION,
            filter,
            null,
            order
        )
        if (cur!!.moveToFirst()) {
            do {

                val name = cur.getString(1)
                val emlAddr = cur.getString(3)
                // keep unique only
                Log.d("ContactEmail", "name $name and email $emlAddr")
            } while (cur.moveToNext())
        }

        cur.close()
    }

    private fun fetchContacts(context: Context): MutableList<Contact> {

        val contacts: MutableList<Contact> = ArrayList()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        fetchContactsEmail()
        if ((cursor?.count ?: 0) > 0) {
            while (cursor!!.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                val phoneWihtoutCountryCode =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val phoneNo = Utils.getPhoneWithoutCountryCode(phoneWihtoutCountryCode)
                val countryCode = Utils.getPhoneNumberCountryCode(phoneWihtoutCountryCode)

                val email =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))

                val photoContentUri =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                var photoUri: Uri? = null
                if (photoContentUri != null) {
                    val photoId =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID))
                    val person = ContentUris.withAppendedId(
                        ContactsContract.Contacts.CONTENT_URI, java.lang.Long
                            .parseLong(photoId)
                    )
                    photoUri = Uri.withAppendedPath(
                        person,
                        ContactsContract.Contacts.Photo.CONTENT_DIRECTORY
                    )
                }
                Log.d("contact", "getAllContacts: $name $countryCode $phoneNo $email  ${photoUri.toString()}")
                val contact = Contact(
                    name,
                    countryCode,
                    phoneNo,
                    email,
                    photoUri.toString(),
                    false, null
                )
                contacts.add(contact)
            }
        }
        cursor?.close()
        return contacts
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Contact>) {
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Contact>) {
    }

    private fun updateState(state: PagingState) {
        this.state.postValue(state)
    }
}