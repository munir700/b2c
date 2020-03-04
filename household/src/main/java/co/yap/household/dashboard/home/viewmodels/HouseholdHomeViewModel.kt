package co.yap.household.dashboard.home.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.household.dashboard.home.interfaces.IHouseholdHome
import co.yap.household.dashboard.home.states.HouseholdHomeState
import co.yap.household.dashboard.main.viewmodels.HouseholdDashboardBaseViewModel
import co.yap.modules.yapnotification.models.Notification
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.managers.MyUserManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

class HouseholdHomeViewModel(application: Application) :
    HouseholdDashboardBaseViewModel<IHouseholdHome.State>(application),
    IHouseholdHome.ViewModel, IRepositoryHolder<TransactionsRepository> {

    override val repository: TransactionsRepository = TransactionsRepository
    override val state: HouseholdHomeState =
        HouseholdHomeState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var viewState: MutableLiveData<Int> = MutableLiveData(Constants.EVENT_LOADING)
    override var notificationList: MutableLiveData<ArrayList<Notification>> = MutableLiveData()
    override val transactionsLiveData: MutableLiveData<List<HomeTransactionListData>> =
        MutableLiveData(arrayListOf())
    override val isLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isLast: MutableLiveData<Boolean> = MutableLiveData(false)
    override var homeTransactionRequest: HomeTransactionsRequest =
        HomeTransactionsRequest(0, 30, null, null, null)
    override var MAX_CLOSING_BALANCE: Double = 0.0
    var closingBalanceArray: ArrayList<Double> = arrayListOf()


    override fun onCreate() {
        super.onCreate()
        requestTransactions()
    }

    override fun onResume() {
        super.onResume()
        if (Constants.USER_STATUS_CARD_ACTIVATED == MyUserManager.user?.notificationStatuses)
            checkUserStatus()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun checkUserStatus() {
        when (MyUserManager.user?.notificationStatuses) {
            Constants.USER_STATUS_ON_BOARDED -> {
                viewState.value = Constants.EVENT_EMPTY
                notificationList.value?.add(
                    Notification(
                        "Set your card PIN",
                        "Now create a unique 4-digit PIN code to be able to use your debit card for purchases and withdrawals",
                        "",
                        Constants.NOTIFICATION_ACTION_SET_PIN,
                        "",
                        ""
                    )
                )

            }
            Constants.USER_STATUS_MEETING_SUCCESS -> {
                viewState.value = Constants.EVENT_EMPTY
                notificationList.value?.add(
                    Notification(
                        "Complete Verification",
                        "Complete verification to activate your account",
                        "",
                        Constants.NOTIFICATION_ACTION_COMPLETE_VERIFICATION,
                        "",
                        ""
                    )
                )
            }
            Constants.USER_STATUS_MEETING_SCHEDULED -> {
                viewState.value = Constants.EVENT_EMPTY
                notificationList.value?.clear()
            }
            Constants.USER_STATUS_CARD_ACTIVATED -> {
                viewState.value = Constants.EVENT_EMPTY
                notificationList.value?.clear()
            }
        }

        if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus) {
            viewState.value = Constants.EVENT_CONTENT
            notificationList.value?.clear()
        } else {
            viewState.value = Constants.EVENT_EMPTY
        }
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
                            val contentsList: ArrayList<Content> = arrayListOf()

                            for (transactionsDay in lists.value) {
                                contentsList.addAll(transactionsDay.content)

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
                                    if (parentItem.content.size != oldTransactionList[i].content.size) {
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
                            viewState.value = Constants.EVENT_EMPTY
                        } else {
                            state.transactionList.set(sortedCombinedTransactionList)
                            viewState.value = Constants.EVENT_CONTENT
                        }
                    }
                    transactionsLiveData.value = sortedCombinedTransactionList
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    viewState.value = Constants.EVENT_EMPTY
                    /*/isRefreshing.value = false*/
                }
            }

        }
    }

    private fun setUpSectionHeader(response: RetroApiResponse.Success<HomeTransactionsResponse>): ArrayList<HomeTransactionListData> {
        val contentList = response.data.data.content as ArrayList<Content>
        contentList.sortWith(Comparator { o1, o2 -> o2.creationDate?.compareTo(o1.creationDate!!)!! })
        val groupByDate = contentList.groupBy { item ->
            convertDate(item.creationDate!!)
        }

        val transactionModelData: ArrayList<HomeTransactionListData> =
            arrayListOf()

        for (transactionsDay in groupByDate.entries) {

            val contentsList = transactionsDay.value as ArrayList<Content>
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