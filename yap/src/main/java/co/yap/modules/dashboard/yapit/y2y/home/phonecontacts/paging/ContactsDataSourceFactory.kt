package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.Contact

class ContactsDataSourceFactory(private val repo: TransactionsRepository) :
    DataSource.Factory<Long, Contact>() {

    val contactDataSourceLiveData = MutableLiveData<ContactsDataSource>()

    override fun create(): DataSource<Long, Contact> {
        val contactDataSource = ContactsDataSource(repo)
        contactDataSourceLiveData.postValue(contactDataSource)
        return contactDataSource
    }
}