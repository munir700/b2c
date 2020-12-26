package co.yap.modules.dashboard.transaction.search

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository.searchTransactions
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.UTC

class TransactionSearchViewModel(application: Application) :
    BaseViewModel<ITransactionSearch.State>(application), ITransactionSearch.ViewModel {
    override val state = TransactionSearchState()
    override var transactionAdapter: ObservableField<HomeTransactionAdapter>? = ObservableField()
    override var clickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun requestTransactions(
        transactionRequest: HomeTransactionsRequest?,
        isLoadMore: Boolean, apiResponse: ((State?, HomeTransactionListData?) -> Unit?)?
    ) {
        //cancelAllJobs()
        launch {
            //delay(500)
            if (transactionRequest?.number == 0) state.transactionMap?.value = null
            if (!isLoadMore)
                state.stateLiveData?.value = State.loading(null)
            when (val response =
                searchTransactions(state.transactionRequest)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.transaction.isNotEmpty()) {
                        state.stateLiveData?.value = State.success(null)
                        var tempMap: Map<String?, List<Transaction>>
                        tempMap =
                            response.data.data.transaction.sortedByDescending { t ->
                                DateUtils.stringToDate(
                                    t.creationDate ?: "",
                                    DateUtils.SERVER_DATE_FORMAT,
                                    UTC
                                )?.time
                            }
                                .distinct().groupBy { t ->
                                    DateUtils.reformatDate(
                                        t.creationDate,
                                        DateUtils.SERVER_DATE_FORMAT,
                                        DateUtils.FORMAT_DATE_MON_YEAR, UTC
                                    )
                                }
                        state.transactionMap?.value = state.transactionMap?.value?.let {
                            tempMap.mergeReduce(other = it)
                        } ?: tempMap
//                        state.transactionMap?.value = tempMap
                        transactionAdapter?.get()?.setTransactionData(state.transactionMap?.value)

//                        apiResponse?.invoke(State.success(null),response.data.data)
                    } else {

                        if (state.transactionMap?.value == null) {
                            state.stateLiveData?.value = State.empty(null)
                            transactionAdapter?.get()?.setTransactionData(emptyMap())
                        } else {
                            state.stateLiveData?.value = State.error(null)
                        }
//                        if (!isLoadMore)

//                        apiResponse?.invoke(State.success(null),response.data.data)
                    }

                    apiResponse?.invoke(state.stateLiveData?.value, response.data.data)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.stateLiveData?.value = State.network(null)
                    apiResponse?.invoke(state.stateLiveData?.value, null)

                }
            }
        }
    }


    override fun getPaginationListener(): PaginatedRecyclerView.Pagination? {
        return object : PaginatedRecyclerView.Pagination() {
            override fun notifyPaginationRestart() {
                super.notifyPaginationRestart()
                cancelAllJobs()
            }

            override fun onNextPage(page: Int) {
                state.transactionRequest?.number = page
                requestTransactions(state.transactionRequest, page != 0) { state, date ->
                    notifyPageLoaded()
                    if (date?.last == true || state?.status == Status.ERROR) {
                        notifyPaginationCompleted()
                    }
                }
            }
        }
    }

    fun <K, V> Map<K, V>.mergeReduce(
        other: Map<K, V>,
        reduce: (V, V) -> V = { a, b -> b }
    ): Map<K, V> {
        val result = LinkedHashMap<K, V>(this.size + other.size)
        result.putAll(this)
        other.forEach { e -> result[e.key] = result[e.key]?.let { reduce(e.value, it) } ?: e.value }
        return result
    }
}