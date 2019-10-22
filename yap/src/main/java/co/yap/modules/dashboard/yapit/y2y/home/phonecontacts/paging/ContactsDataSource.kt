package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.store.StoresRepository
import co.yap.networking.store.requestdtos.CreateStoreRequest
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.Contact
import co.yap.yapcore.helpers.PagingState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactsDataSource(
    private val repo: TransactionsRepository
) :
    PageKeyedDataSource<Long, Contact>() {

    var state: MutableLiveData<PagingState> = MutableLiveData()

    fun retry() {

    }

    private fun setRetry() {

    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Contact>
    ) {
        updateState(PagingState.LOADING)
        GlobalScope.launch {
            when (val response =
                repo.getCardFee("CreateStoreRequest())")) {
                is RetroApiResponse.Success -> {
//                    callback.onResult(
//                        response.data.stores,
//                        null,
//                        2
//                    )
                    updateState(PagingState.DONE)
                }
                is RetroApiResponse.Error -> {
//                    callback.onResult(
//                        getDummyList().take(2),
//                        null,
//                        null
//                    )
                    updateState(PagingState.DONE)

                    //setRetry(Action { loadInitial(params, callback) })
                    //callback.onResult(listOf(), 111L, 1221L)
                    //updateState(PagingState.ERROR)
                }
            }
        }
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