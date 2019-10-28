package co.yap.modules.dashboard.store.paging.transactions

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import co.yap.modules.dashboard.viewmodels.YapHomeViewModel
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.yapcore.helpers.PagingState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class TransactionsDataSource(
    private val storeRepo: TransactionsRepository,
    val yapHomeViewModel: YapHomeViewModel
) :
    PageKeyedDataSource<Long, HomeTransactionListData>() {

    var pageNumber: Int = 1
    var getPages: Int = 20
    //
    var minAmount: Double = 0.00
    var maxAmount: Double = 20000.00
    var creditSearch: Boolean = true
    var debitSearch: Boolean = true
    var yapYoungTransfer: Boolean = true

    var state: MutableLiveData<PagingState> = MutableLiveData()

    fun retry() {

    }

    private fun setRetry() {

    }


    var homeTransactionsRequest: HomeTransactionsRequest =
        HomeTransactionsRequest(
            pageNumber,
            getPages,
            minAmount,
            maxAmount,
            creditSearch,
            debitSearch,
            yapYoungTransfer
        )

    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Long>,
        callback: PageKeyedDataSource.LoadInitialCallback<Long, HomeTransactionListData>
    ) {

        updateState(PagingState.LOADING)
          pageNumber = 1
        GlobalScope.launch {
            when (val response =
                storeRepo.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {

                    var transactionModelData: java.util.ArrayList<HomeTransactionListData> =
                        setUpSectionHeader(response)
                    if (response.data.data.pageable.offset < response.data.data.totalElements) {

//                    homeTransactionsRequest  =  HomeTransactionsRequest(pageNumber, getPages, minAmount, maxAmount, creditSearch, debitSearch, yapYoungTransfer)
                        pageNumber = response.data.data.pageable.pageNumber + 1

                        callback.onResult(
                            transactionModelData,
                            response.data.data.pageable.pageNumber.toLong(),
                            response.data.data.pageable.pageNumber.toLong() + 1
                        )
                    } else {

                        updateState(PagingState.LOADING)
                    }
                }
                is RetroApiResponse.Error -> {
                    updateState(PagingState.DONE)

                }
            }
        }
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
                    var transactionModelData: java.util.ArrayList<HomeTransactionListData> =
                        setUpSectionHeader(response)
                    if (response.data.data.pageable.offset < response.data.data.totalElements) {

                        pageNumber = response.data.data.pageable.pageNumber + 1

                        callback.onResult(
                            transactionModelData,
                            pageNumber.toLong()
                            /*params.key + 1*/
                        )
                        updateState(PagingState.LOADING)
                    } else {


                        updateState(PagingState.DONE)
                    }
                }
                is RetroApiResponse.Error -> {
                    callback.onResult(listOf(), 111L)
                    updateState(PagingState.ERROR)
                }
            }
        }
    }

    private fun setUpSectionHeader(response: RetroApiResponse.Success<HomeTransactionsResponse>): ArrayList<HomeTransactionListData> {
        yapHomeViewModel.contentList = response.data.data.content as ArrayList<Content>
        Collections.sort(yapHomeViewModel.contentList, object :
            Comparator<Content> {
            override fun compare(
                o1: Content,
                o2: Content
            ): Int {
                return o2.creationDate!!.compareTo(o1.creationDate!!)
            }
        })

        val groupByDate = yapHomeViewModel.contentList.groupBy { item ->
            yapHomeViewModel.convertDate(item.creationDate!!)
        }

        println(groupByDate.entries.joinToString(""))

        var transactionModelData: ArrayList<HomeTransactionListData> =
            arrayListOf()

        for (transactionsDay in groupByDate.entries) {


            var contentsList: ArrayList<Content> = arrayListOf()
            println(transactionsDay.key)
            println(transactionsDay.value)
            contentsList = transactionsDay.value as ArrayList<Content>
            contentsList.sortByDescending { it ->
                it.creationDate
            }

            var closingBalanceOfTheDay: Double = contentsList.get(0).balanceAfter
            yapHomeViewModel.closingBalanceArray.add(closingBalanceOfTheDay)

            var transactionModel: HomeTransactionListData = HomeTransactionListData(
                "Type",
                "AED",
                transactionsDay.key!!,
                contentsList.get(0).totalAmount.toString(),
                contentsList.get(0).balanceAfter,
                80.00 /*  "calculate the percentage as per formula from the keys".toDouble()*/,
                contentsList,

                response.data.data.first,
                response.data.data.last,
                response.data.data.number,
                response.data.data.numberOfElements,
                response.data.data.pageable,
                response.data.data.size,
                response.data.data.sort,
                response.data.data.totalElements,
                response.data.data.totalPages
            )
            transactionModelData.add(transactionModel)

            yapHomeViewModel.transactionLogicHelper.transactionList =
                transactionModelData
            yapHomeViewModel.MAX_CLOSING_BALANCE =
                yapHomeViewModel.closingBalanceArray.max()!!
        }
        return transactionModelData
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