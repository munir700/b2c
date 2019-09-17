package co.yap.modules.store.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import co.yap.modules.store.interfaces.IYapStore
import co.yap.modules.store.paging.StoreDataSource
import co.yap.modules.store.paging.StoreDataSourceFactory
import co.yap.modules.store.states.YapStoreState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.store.StoresRepository
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState

class YapStoreViewModel(application: Application) : BaseViewModel<IYapStore.State>(application),
    IYapStore.ViewModel,
    IRepositoryHolder<StoresRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapStoreState = YapStoreState()
    override val repository: StoresRepository = StoresRepository
    override lateinit var storesLiveData: LiveData<PagedList<Store>>
    private val storeSourceFactory: StoreDataSourceFactory

    init {
        storeSourceFactory = StoreDataSourceFactory(repository)
        storesLiveData = LivePagedListBuilder<Long, Store>(
            storeSourceFactory,
            getPagingConfigs()
        ).build()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getState(): LiveData<PagingState> = Transformations.switchMap<StoreDataSource,
            PagingState>(storeSourceFactory.storeDataSourceLiveData, StoreDataSource::state)

    override fun listIsEmpty(): Boolean {
        return storesLiveData.value?.isEmpty() ?: true
    }

    override fun retry() {
        storeSourceFactory.storeDataSourceLiveData.value?.retry()
    }

    private fun getPagingConfigs(): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(30)
            .setPrefetchDistance(1)
            .setInitialLoadSizeHint(30 * 2)
            .setEnablePlaceholders(true)
            .build()
    }
}