package co.yap.modules.dashboard.home.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.states.YapHomeState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.HomeNotification
import co.yap.networking.notification.NotificationAction
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.TransactionsRepository.getFailedTransactions
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.*
import co.yap.yapcore.helpers.extentions.getFormattedDate
import co.yap.yapcore.managers.MyUserManager

class YapHomeViewModel(application: Application) :
    YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    override var txnFilters: TransactionFilters = TransactionFilters()

    private val cardsRepository: CardsRepository = CardsRepository
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val transactionsLiveData: MutableLiveData<List<HomeTransactionListData>> =
        MutableLiveData()
    override var isLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isLast: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    var sortedCombinedTransactionList: ArrayList<HomeTransactionListData> = arrayListOf()
    override var MAX_CLOSING_BALANCE: Double = 0.0
    var closingBalanceArray: ArrayList<Double> = arrayListOf()


    init {
        YAPApplication.clearFilters()
    }

    override fun onCreate() {
        super.onCreate()
        requestAccountTransactions()
        getDebitCards()
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
        launch {
            if (isLoadMore.value == false)
                state.loading = true
            when (val response =
                transactionsRepository.getAccountTransactions(YAPApplication.homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    isLast.value = response.data.data.last
                    val transactionModelData = setUpSectionHeader(response)

                    if (isRefreshing.value == true) {
                        sortedCombinedTransactionList.clear()
                    }
                    isRefreshing.value = false

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

                            var transactionModel = HomeTransactionListData(
                                "Type",
                                "AED",
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
                    transactionsLiveData.value = sortedCombinedTransactionList
                    isLoadMore.value = false
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    isRefreshing.value = false
                    isLoadMore.value = false
                }
            }
        }
        state.loading = false
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

            val transactionModel = HomeTransactionListData(
                "Type",
                "AED",
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
                contentsList[0].creationDate.toString()
            )
            transactionModelData.add(transactionModel)
            MAX_CLOSING_BALANCE =
                closingBalanceArray.max() ?: 0.0
        }
        return transactionModelData
    }

    override fun getDebitCards() {
        launch {
            when (val response = cardsRepository.getDebitCards("DEBIT")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            val primaryCard = getPrimaryCard(response.data.data)
                            MyUserManager.card.value = primaryCard
                        } else {
                            state.toast = "Debit card not found.^${AlertType.TOAST.name}"
                        }
                    }
                }
                is RetroApiResponse.Error ->
                    state.toast = "${response.error.message}^${AlertType.TOAST.name}"
            }
        }
    }

    private fun getPrimaryCard(cards: ArrayList<Card>?): Card? {
        return cards?.firstOrNull { it.cardType == CardType.DEBIT.type }
    }

    override fun getNotifications(
        accountInfo: AccountInfo,
        paymentCard: Card, apiResponse: ((Boolean) -> Unit?)?
    ) {
        val list: MutableList<HomeNotification> = mutableListOf()
        if (accountInfo.otpBlocked == true) {
            list.add(
                HomeNotification(
                    id = "1",
                    description = "Some features may appear blocked for you as you made too many incorrect OTP attempts. Call or chat with us now to get full access.",
                    action = NotificationAction.HELP_AND_SUPPORT
                )
            )
        }
        if ((accountInfo.notificationStatuses == AccountStatus.ON_BOARDED.name || accountInfo.notificationStatuses == AccountStatus.CAPTURED_EID.name
                    || accountInfo.notificationStatuses == AccountStatus.CAPTURED_ADDRESS.name
                    || accountInfo.notificationStatuses == AccountStatus.BIRTH_INFO_COLLECTED.name
                    || accountInfo.notificationStatuses == AccountStatus.MEETING_SCHEDULED.name)
            && accountInfo.partnerBankStatus != PartnerBankStatus.ACTIVATED.status
        ) {
            list.add(
                HomeNotification(
                    id = "2",
                    title = "Complete Verification",
                    description = "Complete verification to activate your account.",
                    action = NotificationAction.COMPLETE_VERIFICATION
                )
            )
        }

        if (shouldShowSetPin(paymentCard) && accountInfo.partnerBankStatus == PartnerBankStatus.ACTIVATED.status) {
            list.add(
                HomeNotification(
                    id = "3",
                    title = "Set PIN",
                    description = "Now create a unique 4-digit PIN to be able to use your primary card for purchases and withdrawals.",
                    action = NotificationAction.SET_PIN
                )
            )
        }
        if ((accountInfo.notificationStatuses == AccountStatus.EID_EXPIRED.name
                    || accountInfo.notificationStatuses == AccountStatus.EID_RESCAN_REQ.name)
            && accountInfo.partnerBankStatus == PartnerBankStatus.ACTIVATED.status
        ) {
            list.add(
                HomeNotification(
                    id = "4",
                    title = "Renewed ID",
                    description = "Your Emirates ID has expired. Please update your account with the renewed ID as soon as you can.",
                    action = NotificationAction.UPDATE_EMIRATES_ID
                )
            )

        }
        state.notificationList.value?.addAll(list)
        apiResponse?.invoke(true)
    }

    private fun shouldShowSetPin(paymentCard: Card): Boolean {
        return (paymentCard.deliveryStatus == CardDeliveryStatus.SHIPPED.name && !paymentCard.pinCreated)
    }

    override fun getFailedTransactionAndSubNotifications(apiResponse: ((Boolean) -> Unit?)?) {
        launch {
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
}

