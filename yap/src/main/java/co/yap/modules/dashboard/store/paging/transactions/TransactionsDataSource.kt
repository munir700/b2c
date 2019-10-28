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

    fun abc() {


//    launch {
//        state.loading = true
//
//        var homeTransactionsRequest: HomeTransactionsRequest =
//            HomeTransactionsRequest(1, 5, 10.00, 100.00, true, true, true)
//
//        when (val response =
//            transactionsRepository.getAccountTransactions(homeTransactionsRequest)) {
//            is RetroApiResponse.Success -> {
//                Log.i(
//                    "getAccountTransactions",
//                    response.data.toString()
//                )
//                if (null != response.data.data) {
//
//                    loadJSONDummyList()
//                    Collections.sort(contentList, object :
//                        Comparator<Content> {
//                        override fun compare(
//                            o1: Content,
//                            o2: Content
//                        ): Int {
//                            return o2.updatedDate.compareTo(o1.updatedDate)
//                        }
//                    })
//
//                    val groupByDate = contentList.groupBy { item ->
//                        convertDate(item.updatedDate)
////                            item.updatedDate
//                    }
//
//                    println(groupByDate.entries.joinToString(""))
//
//                    var transactionModelData: java.util.ArrayList<HomeTransactionListData> =
//                        arrayListOf()
//
//                    for (transactionsDay in groupByDate.entries) {
//
//
//                        var contentsList: java.util.ArrayList<Content> = arrayListOf()
//                        println(transactionsDay.key)
//                        println(transactionsDay.value)
//                        contentsList = transactionsDay.value as java.util.ArrayList<Content>
//                        contentsList.sortByDescending { it ->
//                            it.updatedDate
//                        }
//
//                        var closingBalanceOfTheDay: Double = contentsList.get(0).balanceAfter
//                        closingBalanceArray.add(closingBalanceOfTheDay)
////                            var calculateTotalAmount: Double = 0.0
////
////                            for (contentValue in transactionsDay.value) {
////                                calculateTotalAmount = calculateTotalAmount + contentValue.amount
////                                println(calculateTotalAmount)
////                            }
//
//
//                        var transactionModel: HomeTransactionListData = HomeTransactionListData(
//                            "Type",
//                            "AED",
//                            transactionsDay.key!!,
//                            contentsList.get(0).totalAmount.toString(),
//                            contentsList.get(0).balanceAfter,
//                            80.00 /*  "calculate the percentage as per formula from the keys".toDouble()*/,
//
//                            contentsList
//
//                        )
//                        transactionModelData.add(transactionModel)// this should be that main list
//
//                        transactionLogicHelper.transactionList = transactionModelData
//                        MAX_CLOSING_BALANCE = closingBalanceArray.max()!!
//                    }
//                }
//                //                            calculateCummulativeClosingBalance(closingBalanceArray)
//            }
//
//            is RetroApiResponse.Error -> {
//
//            }
//        }
//
//        state.loading = false
//    }
    }
    var homeTransactionsRequest: HomeTransactionsRequest =
        HomeTransactionsRequest(1, 5, 10.00, 100.00, true, true, true)

    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Long>,
        callback: PageKeyedDataSource.LoadInitialCallback<Long, HomeTransactionListData>
    ) {

        var homeTransactionsRequest: HomeTransactionsRequest =
            HomeTransactionsRequest(1, 5, 10.00, 100.00, true, true, true)
        updateState(PagingState.LOADING)
        GlobalScope.launch {
            when (val response =
                storeRepo.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    callback.onResult(
                        response.data.data /*(HomeTransactionListData can't cast ->)*/   as MutableList<HomeTransactionListData>,// its crashing at this line because HomeTransactionListData from response is equivalnat to HomeTransactionListData
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
                    callback.onResult(
                        response.data.data   as MutableList<HomeTransactionListData>,
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