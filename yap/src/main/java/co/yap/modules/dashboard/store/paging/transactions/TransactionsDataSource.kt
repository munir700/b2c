package co.yap.modules.dashboard.store.paging.transactions

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.helpers.PagingState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TransactionsDataSource(private val storeRepo: TransactionsRepository/*, homeTransactionsRequest : HomeTransactionsRequest*/) :
    PageKeyedDataSource<Long, HomeTransactionListData>() {

    var state: MutableLiveData<PagingState> = MutableLiveData()

    fun retry() {

    }

    private fun setRetry() {

    }


    var homeTransactionsRequest: HomeTransactionsRequest =
        HomeTransactionsRequest(1, 30, 0.00, 20000.00, true, true, true)

    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Long>,
        callback: PageKeyedDataSource.LoadInitialCallback<Long, HomeTransactionListData>
    ) {

        var homeTransactionListData: HomeTransactionListData
        var homeTransactionsRequest: HomeTransactionsRequest =
            HomeTransactionsRequest(1, 30, 0.00, 20000.00, true, true, true)
        updateState(PagingState.LOADING)
        GlobalScope.launch {
            when (val response =
                storeRepo.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    val homeTransactionsListData: ArrayList<HomeTransactionListData> = arrayListOf()
                    homeTransactionsListData.add(response.data.data)
                    response.data.data

                    callback.onResult(
                        homeTransactionsListData /*(HomeTransactionListData can't cast ->)*/,// its crashing at this line because HomeTransactionListData from response is equivalnat to HomeTransactionListData
                        response.data.data.pageable.pageNumber.toLong(),
                        response.data.data.pageable.pageNumber.toLong() + 1
                    )
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

//        callback.onResult(
//            getDummyList().take(2),   // this will show items in the length
//            null,
//            null
//        )
    }

    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Long>,
        callback: PageKeyedDataSource.LoadCallback<Long, HomeTransactionListData>
    ) {
        updateState(PagingState.LOADING)
        GlobalScope.launch {
            when (val response =
                storeRepo.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    val homeTransactionsListData: ArrayList<HomeTransactionListData> = arrayListOf()
                    homeTransactionsListData.add(response.data.data)
                     callback.onResult(
                         homeTransactionsListData,
                        params.key + 1
                    )
                    updateState(PagingState.DONE)
                }
                is RetroApiResponse.Error -> {
                    callback.onResult(listOf(), 111L)
                    updateState(PagingState.ERROR)
                }
            }
        }
    }


    override fun loadBefore(
        params: PageKeyedDataSource.LoadParams<Long>,
        callback: PageKeyedDataSource.LoadCallback<Long, HomeTransactionListData>
    ) {
    }

    private fun updateState(state: PagingState) {
        this.state.postValue(state)
    }
}