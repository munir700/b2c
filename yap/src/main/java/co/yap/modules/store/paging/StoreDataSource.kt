package co.yap.modules.store.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.store.StoresRepository
import co.yap.networking.store.requestdtos.CreateStoreRequest
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.helpers.PagingState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StoreDataSource(
    private val storeRepo: StoresRepository
) :
    PageKeyedDataSource<Long, Store>() {

    var state: MutableLiveData<PagingState> = MutableLiveData()
    //private var retryCompletable: Completable? = null

    fun retry() {
//        if (retryCompletable != null) {
//            compositeDisposable.add(
//                retryCompletable!!
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe()
//            )
//        }
    }

    private fun setRetry() {
//        setRetry(action: Action?)
//        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Store>
    ) {
        updateState(PagingState.LOADING)
        GlobalScope.launch {
            when (val response =
                storeRepo.getYapStores(CreateStoreRequest())) {
                is RetroApiResponse.Success -> {
                    callback.onResult(
                        response.data.stores,
                        null,
                        2
                    )
                    updateState(PagingState.DONE)
                }
                //Actual Line is RetroApiResponse.Error -> state.toast = response.error.message
                is RetroApiResponse.Error -> {

                    //setRetry(Action { loadInitial(params, callback) })
                    callback.onResult(listOf(), 111L, 1221L)
                    updateState(PagingState.ERROR)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Store>) {
        updateState(PagingState.LOADING)
        GlobalScope.launch {
            when (val response =
                storeRepo.getYapStores(CreateStoreRequest())) {
                is RetroApiResponse.Success -> {
                    callback.onResult(
                        response.data.stores,
                        params.key + 1
                    )
                    updateState(PagingState.DONE)
                }
                //Actual Line is RetroApiResponse.Error -> state.toast = response.error.message
                is RetroApiResponse.Error -> {
                    callback.onResult(listOf(), 111L)
                    updateState(PagingState.ERROR)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Store>) {
    }

    private fun updateState(state: PagingState) {
        this.state.postValue(state)
    }
}