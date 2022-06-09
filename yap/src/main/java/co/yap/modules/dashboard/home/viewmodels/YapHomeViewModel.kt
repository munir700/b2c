package co.yap.modules.dashboard.home.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.config.FeatureFlagIds
import co.yap.config.FeatureFlagToggle
import co.yap.modules.dashboard.home.enums.EnumWidgetTitles
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.states.YapHomeState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.TransactionsRepository.getFailedTransactions
import co.yap.networking.transactions.responsedtos.categorybar.MonthData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.State
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.enums.CardStatus
import co.yap.yapcore.enums.PaymentCardStatus
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.helpers.extentions.getFormattedDate
import co.yap.yapcore.leanplum.UserAttributes
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class YapHomeViewModel(application: Application) :
    YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    override var txnFilters: TransactionFilters = TransactionFilters()
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    private val customerRepository: CustomersRepository = CustomersRepository
    override val transactionsLiveData: MutableLiveData<List<HomeTransactionListData>> =
        MutableLiveData()
    override var isLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isLast: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    override var MAX_CLOSING_BALANCE: Double = 0.0
    override var monthData: MutableLiveData<List<MonthData>>? = MutableLiveData()
    override var dashboardWidgetList: MutableLiveData<List<WidgetData>> = MutableLiveData()
    override var widgetList: List<WidgetData> = ArrayList()
    var sortedCombinedTransactionList: ArrayList<HomeTransactionListData> = arrayListOf()
    var closingBalanceArray: ArrayList<Double> = arrayListOf()

    init {
        YAPApplication.clearFilters()
    }

    override fun onCreate() {
        super.onCreate()
        requestAccountTransactions()
        SessionManager.getDebitCard()
    }

    override fun filterTransactions() {
        MAX_CLOSING_BALANCE = 0.0
        closingBalanceArray.clear()

        if (!sortedCombinedTransactionList.isNullOrEmpty()) {
            sortedCombinedTransactionList.clear()
        }
        requestAccountTransactions()
    }

    override fun requestAccountTransactions() {
        launch(Dispatcher.LongOperation) {
            if (isLoadMore.value == false) {
                // state.loading = true
                state.showTxnShimmer.postValue(State.loading(null))
            }
            when (val response =
                transactionsRepository.getAccountTransactions(YAPApplication.homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    isLast.postValue(response.data.data.last)
                    val transactionModelData = setUpSectionHeader(response)

                    if (isRefreshing.value == true) {
                        sortedCombinedTransactionList.clear()
                    }
                    isRefreshing.postValue(false)

                    if (sortedCombinedTransactionList != transactionModelData) {
                        sortedCombinedTransactionList.addAll(transactionModelData)
                    }

                    val unionList =
                        (sortedCombinedTransactionList.asSequence() + transactionModelData.asSequence())
                            .distinct()
                            .groupBy { it.date }

                    for (lists in unionList.entries) {
                        if (lists.value.size > 1) {// sortedCombinedTransactionList.equals(transactionModelData fails in this case
                            val transactionList: ArrayList<Transaction> = arrayListOf()
                            for (transactionsDay in lists.value) {
                                transactionList.addAll(transactionsDay.transaction)

                            }
                            transactionList.sortByDescending { it ->
                                it.creationDate
                            }

                            val closingBalanceOfTheDay = transactionList[0].balanceAfter ?: 0.0
                            closingBalanceArray.add(closingBalanceOfTheDay)

                            val transactionModel = HomeTransactionListData(
                                "Type",
                                SessionManager.getDefaultCurrency(),
                                /* transactionsDay.key!!*/
                                transactionList[0].getFormattedDate(),
                                transactionList[0].totalAmount.toString(),
                                transactionList[0].balanceAfter,
                                0.00 /*  "calculate the percentage as per formula from the keys".toDouble()*/,
                                transactionList,
                                response.data.data.first,
                                response.data.data.last,
                                response.data.data.number,
                                response.data.data.numberOfElements,
                                response.data.data.pageable,
                                response.data.data.size,
                                response.data.data.sort,
                                response.data.data.totalElements,
                                response.data.data.totalPages,
                                transactionList[0].creationDate
                            )
                            var numberstoReplace: Int = 0
                            var replaceNow: Boolean = false
                            val iterator = sortedCombinedTransactionList.iterator()
                            while (iterator.hasNext()) {
                                val item = iterator.next()
                                if (item.date.equals(transactionList[0].getFormattedDate())) {
                                    numberstoReplace = sortedCombinedTransactionList.indexOf(item)
                                    iterator.remove()
                                    replaceNow = true
                                }
                            }
                            if (replaceNow) {
                                sortedCombinedTransactionList.add(
                                    numberstoReplace,
                                    transactionModel
                                )
                                replaceNow = false
                            }
                        }
                    }
                    if (isLoadMore.value == false) {
                        state.showTxnShimmer.postValue(State.success(null))
                    }
                    transactionsLiveData.postValue(sortedCombinedTransactionList)
                    //isLoadMore.value = false
                    //state.loading = false
                }
                is RetroApiResponse.Error -> {
                    // state.loading = false
                    isRefreshing.postValue(false)
                    isLoadMore.postValue(false)
                    state.showTxnShimmer.postValue(State.error(""))
                }
            }
        }
//        state.loading = false
    }

    override fun loadMore() {
        requestAccountTransactions()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun setUpSectionHeader(response: RetroApiResponse.Success<HomeTransactionsResponse>): ArrayList<HomeTransactionListData> {
//        val transactionList = response.data.data.transaction as ArrayList<Transaction>
//        transactionList.sortWith(Comparator { firstObject, secondObject ->
//            secondObject.creationDate?.compareTo(firstObject?.creationDate ?: "") ?: 0
//        })

        val transactionGroupByDate =
            (response.data.data.transaction as ArrayList<Transaction>).groupBy { item ->
                item.getFormattedDate()
            }
        val transactionModelData: ArrayList<HomeTransactionListData> = arrayListOf()
        transactionGroupByDate.entries.forEach { mapEntry ->

            val contentsList = mapEntry.value as ArrayList<Transaction>
            contentsList.sortByDescending { it ->
                it.getFormattedDate()
            }

            val closingBalanceOfTheDay: Double = contentsList[0].balanceAfter ?: 0.0
            closingBalanceArray.add(closingBalanceOfTheDay)

            val creationDate = contentsList[0].creationDate

            val transactionModel = HomeTransactionListData(
                "Type",
                SessionManager.getDefaultCurrency(),
                mapEntry.key,
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
                response.data.data.totalPages,
                creationDate,
                monthYear = DateUtils.getMonthWithYear(
                    SimpleDateFormat(DateUtils.SERVER_DATE_FORMAT).parse(
                        creationDate
                    )
                ),
                dateForBalance = DateUtils.changeZoneAndFormatDateWithDay(creationDate.toString()),
                suffixForDay = DateUtils.getSuffixFromDate(creationDate.toString()),
                balanceYear = DateUtils.getYearFromDate(
                    creationDate.toString(),
                    true,
                    ","
                )
            )
            transactionModelData.add(transactionModel)
            MAX_CLOSING_BALANCE =
                closingBalanceArray.maxOrNull() ?: 0.0
        }
        return transactionModelData
    }

    override fun getNotifications(
        accountInfo: AccountInfo,
        paymentCard: Card, apiResponse: ((Boolean) -> Unit?)?
    ): MutableList<HomeNotification> {
        state.notificationList.value?.addAll(
            NotificationHelper.getNotifications(
                accountInfo,
                paymentCard,
                context
            )
        )
        apiResponse?.invoke(true)
        return state.notificationList.value ?: mutableListOf()
    }

    override fun shouldShowSetPin(paymentCard: Card): Boolean {
        return when {
            paymentCard.status == PaymentCardStatus.INACTIVE.name && paymentCard.deliveryStatus == CardDeliveryStatus.SHIPPED.name -> true
            paymentCard.status == PaymentCardStatus.ACTIVE.name && !paymentCard.pinCreated -> true
            else -> false
        }
    }

    override fun fetchTransactionDetailsForLeanplum(cardStatus: String?) {
        launch {
            when (val response = transactionsRepository.getTransDetailForLeanplum()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let { resp ->
                        val info: HashMap<String, Any?> = HashMap()
                        info[UserAttributes().primary_card_status] = cardStatus?.let {
                            if (it.isBlank().not() && CardStatus.valueOf(it) == CardStatus.BLOCKED)
                                "frozen"
                            else it.toLowerCase()
                        } ?: ""
                        info[UserAttributes().last_transaction_type] =
                            resp.lastTransactionType ?: ""
                        info[UserAttributes().last_transaction_time] =
                            resp.lastTransactionTime ?: ""
                        info[UserAttributes().last_pos_txn_category] =
                            resp.lastPOSTransactionCategory ?: ""
                        info[UserAttributes().total_transaction_count] =
                            resp.totalTransactionCount ?: ""
                        info[UserAttributes().total_transaction_value] =
                            resp.totalTransactionValue ?: ""

                        SessionManager.user?.uuid?.let { trackEventWithAttributes(it, info) }
                    }
                }
                is RetroApiResponse.Error -> {
                }
                //     }
            }
        }
    }

    override fun getFailedTransactionAndSubNotifications(apiResponse: ((Boolean) -> Unit?)?) {
        viewModelScope.launch {
            val list: MutableList<HomeNotification> = mutableListOf()
            when (val response = getFailedTransactions()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.size > 0) {
                            list.addAll(it)
                            state.notificationList.value = list
                            apiResponse?.invoke(true)
                        }
                    }

                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(false)
                }
            }
        }
    }

    override fun requestCategoryBarData() {
        launch {
            when (val response = transactionsRepository.requestCategoryBarData()) {
                is RetroApiResponse.Success -> {
                    try {
                        this.monthData?.postValue(response.data.categoryBarData.monthData)
                    } catch (e: Exception) {
                    }
                }
                is RetroApiResponse.Error -> {

                }
            }
        }
    }

    override fun requestDashboardWidget() {
        launch {
            when (val response = customerRepository.getDashboardWidget()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        FeatureFlagToggle().isFeatureEnable(
                            context,
                            FeatureFlagIds.BillPayment().bill_payments
                        ) { hasFlag ->
                            if (hasFlag) {
                                widgetList = it
                                dashboardWidgetList.postValue(getFilteredList(it))
                            } else {
                                val updatedList = it.toMutableList()
                                val index = updatedList.map { it.name }
                                    .indexOf(EnumWidgetTitles.BILLS.title)
                                updatedList.removeAt(index)
                                widgetList = updatedList
                                dashboardWidgetList.postValue(getFilteredList(updatedList))
                            }
                        }
                    }
                }
                is RetroApiResponse.Error -> {

                }
            }
        }
    }

    private fun getFilteredList(widgetList: MutableList<WidgetData>) = widgetList.run {
        this.filter { it.status == true }.toMutableList().also {
            it.add(WidgetData(id = -1, name = "Edit"))
        }
    }
}
