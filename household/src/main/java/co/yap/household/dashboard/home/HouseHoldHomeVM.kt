package co.yap.household.dashboard.home

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.SharedPreferenceManager
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

class HouseHoldHomeVM @Inject constructor(
    override var state: IHouseholdHome.State,
    private val repository: TransactionsRepository
) : DaggerBaseViewModel<IHouseholdHome.State>(), IHouseholdHome.ViewModel {

    var closingBalanceArray: ArrayList<Double> = arrayListOf()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
//    override var stateLiveData: MutableLiveData<State>? = MutableLiveData()
    override val isLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isLast: MutableLiveData<Boolean> = MutableLiveData(false)
    override var homeTransactionRequest: HomeTransactionsRequest =
        HomeTransactionsRequest(0, 30, null, null, null)
    override val transactionsLiveData: MutableLiveData<List<HomeTransactionListData>> =
        MutableLiveData(arrayListOf())
    override var MAX_CLOSING_BALANCE: Double = 0.0

    override fun handlePressOnView(id: Int) {

    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        //state = state as HouseholdHomeState
        // state.toast = "saddsadsasad"
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun requestTransactions(isLoadMore: Boolean) {

        val sortedCombinedTransactionList: ArrayList<HomeTransactionListData> = arrayListOf()
        val oldTransactionList: ArrayList<HomeTransactionListData> = arrayListOf()

        transactionsLiveData.value?.let {
            sortedCombinedTransactionList.addAll(it)
            oldTransactionList.addAll(it)
        }

        launch {
            when (val response =
                repository.getAccountTransactions(homeTransactionRequest)) {
                is RetroApiResponse.Success -> {
                    isLast.value = response.data.data.last
                    val transactionModelData: ArrayList<HomeTransactionListData> =
                        setUpSectionHeader(response)

                    if (false /*isRefreshing.value!!*/) {
                        sortedCombinedTransactionList?.clear()
                    }
                    /*isRefreshing.value!! -->  isRefreshing.value = false*/

                    if (sortedCombinedTransactionList?.equals(transactionModelData) == false) {
                        sortedCombinedTransactionList.addAll(transactionModelData)
                    }

                    val unionList =
                        (sortedCombinedTransactionList.asSequence().plus(transactionModelData.asSequence()))
                            .distinct()
                            .groupBy { it.date }

                    for (lists in unionList!!.entries) {
                        if (lists.value.size > 1) {// sortedCombinedTransactionList.equals(transactionModelData fails in this case
                            val contentsList: ArrayList<Transaction> = arrayListOf()

                            for (transactionsDay in lists.value) {
                                contentsList.addAll(transactionsDay.transaction)

                            }

                            contentsList.sortByDescending {
                                it.creationDate
                            }


                            val closingBalanceOfTheDay: Double = contentsList[0].balanceAfter!!
                            closingBalanceArray.add(closingBalanceOfTheDay)

                            val transactionModel = HomeTransactionListData(
                                "Type",
                                "AED",
                                /* transactionsDay.key!!*/
                                convertDate(contentsList[0].creationDate!!)!!,
                                contentsList[0].totalAmount.toString(),
                                contentsList[0].balanceAfter,
                                0.00 /*  "calculate the percentage as per formula from the keys".toDouble()*/,
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
                            var numbersToReplace = 0
                            var replaceNow = false


                            val iterator = sortedCombinedTransactionList.iterator()
                            while (iterator.hasNext()) {
                                val item = iterator.next()
                                if (item.date == convertDate(contentsList[0].creationDate!!)) {
                                    numbersToReplace = sortedCombinedTransactionList.indexOf(item)
                                    iterator.remove()
                                    replaceNow = true

                                }

                            }
                            if (replaceNow) {
                                sortedCombinedTransactionList?.add(
                                    numbersToReplace,
                                    transactionModel
                                )
                            }
                        }
                    }

                    //
                    if (isLoadMore) {
                        val listToAppend: MutableList<HomeTransactionListData> = mutableListOf()
                        for (parentItem in sortedCombinedTransactionList) {
                            var shouldAppend = false
                            for (i in 0 until oldTransactionList.size) {
                                if (parentItem.date == oldTransactionList[i].date) {
                                    if (parentItem.transaction.size != oldTransactionList[i].transaction.size) {
                                        shouldAppend = true
                                        break
                                    }
                                    shouldAppend = true
                                    break
                                }
                            }
                            if (!shouldAppend)
                                listToAppend.add(parentItem)
                        }
                        state.transactionList.set(listToAppend)
                    } else {
                        if (sortedCombinedTransactionList.isEmpty()) {
                            stateLiveData?.value  = State.empty(null)
                        } else {
                            state.transactionList.set(sortedCombinedTransactionList)
                            stateLiveData?.value  = State.success(null)
                        }
                    }
                    transactionsLiveData.value = sortedCombinedTransactionList
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    stateLiveData?.value  = State.empty(null)
                    /*/isRefreshing.value = false*/
                }
            }

        }
    }

    private fun setUpSectionHeader(response: RetroApiResponse.Success<HomeTransactionsResponse>): ArrayList<HomeTransactionListData> {
        val contentList = response.data.data.transaction as ArrayList<Transaction>
        contentList.sortWith(Comparator { o1, o2 -> o2.creationDate?.compareTo(o1.creationDate!!)!! })
        val groupByDate = contentList.groupBy { item ->
            convertDate(item.creationDate!!)
        }

        val transactionModelData: ArrayList<HomeTransactionListData> =
            arrayListOf()

        for (transactionsDay in groupByDate.entries) {

            val contentsList = transactionsDay.value as ArrayList<Transaction>
            contentsList.sortByDescending {
                it.creationDate
            }

            val closingBalanceOfTheDay: Double = contentsList[0].balanceAfter!!
            closingBalanceArray.add(closingBalanceOfTheDay)

            val transactionModel = HomeTransactionListData(
                "Type",
                "AED",
                transactionsDay.key!!,
                contentsList[0].totalAmount.toString(),
                contentsList[0].balanceAfter,
                0.00 /*  "calculate the percentage as per formula from the keys".toDouble()*/,
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
            MAX_CLOSING_BALANCE =
                closingBalanceArray.max()!!
        }
        return transactionModelData
    }

    override fun loadMore() {
        requestTransactions(true)
    }


    private fun convertDate(creationDate: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        parser.setTimeZone(TimeZone.getTimeZone("UTC"))
        val convertedDate = parser.parse(creationDate)

        val pattern = "MMMM dd, yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.format(convertedDate)

        return date
    }
}