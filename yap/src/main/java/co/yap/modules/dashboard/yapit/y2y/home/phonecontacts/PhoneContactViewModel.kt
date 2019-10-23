package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.paging.ContactsDataSource
import co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.paging.ContactsDataSourceFactory
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState

class PhoneContactViewModel(application: Application) :
    Y2YBaseViewModel<IPhoneContact.State>(application),
    IPhoneContact.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IPhoneContact.State = PhoneContactState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override lateinit var phoneContactLiveData: LiveData<PagedList<Contact>>
    private val contactSourceFactory: ContactsDataSourceFactory

    init {
        contactSourceFactory = ContactsDataSourceFactory(context,repository)
        phoneContactLiveData = LivePagedListBuilder(
            contactSourceFactory,
            getPagingConfigs()
        ).build()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getState(): LiveData<PagingState> = Transformations.switchMap<ContactsDataSource,
            PagingState>(contactSourceFactory.contactDataSourceLiveData, ContactsDataSource::state)

    override fun listIsEmpty(): Boolean {
        return phoneContactLiveData.value?.isEmpty() ?: true
    }

    override fun retry() {
        contactSourceFactory.contactDataSourceLiveData.value?.retry()
    }

    private fun getPagingConfigs(): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(30 * 2)
            .setEnablePlaceholders(false)
            .build()
    }
}