package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.paging

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.PagingState
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
                            2
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

                        //setRetry(Action { loadInitial(params, callback) })
                        //callback.onResult(listOf(), 111L, 1221L)
                        //updateState(PagingState.ERROR)
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

    private fun fetchContacts(context: Context): MutableList<Contact> {

        val contacts: MutableList<Contact> = ArrayList()

        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        if ((cursor?.count ?: 0) > 0) {
            while (cursor!!.moveToNext()) {

                val name =
                    cursor.getString(cursor!!.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNo =
                    cursor.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val email = "abc@yap.co"
                val photoUri =
                    cursor.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                Log.e("contact", "getAllContacts: $name $phoneNo $photoUri")
                val contact = Contact(
                    name,
                    phoneNo,
                    phoneNo,
                    email,
                    false
                )
                contacts.add(contact)
            }
        }
        cursor?.close()
        return contacts
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Contact>) {
//        updateState(PagingState.LOADING)
//        GlobalScope.launch {
//            when (val response =
//                storeRepo.getYapStores(CreateStoreRequest())) {
//                is RetroApiResponse.Success -> {
//                    callback.onResult(
//                        response.data.stores,
//                        params.key + 1
//                    )
//                    updateState(PagingState.DONE)
//                }
//                is RetroApiResponse.Error -> {
//                    callback.onResult(listOf(), 111L)
//                    updateState(PagingState.ERROR)
//                }
//            }
//        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Contact>) {
    }

    private fun updateState(state: PagingState) {
        this.state.postValue(state)
    }
}