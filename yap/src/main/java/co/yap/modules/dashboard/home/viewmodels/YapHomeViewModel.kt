package co.yap.modules.dashboard.home.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.states.YapHomeState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.yapcore.SingleClickEvent
import java.text.SimpleDateFormat
import java.util.*

class YapHomeViewModel(application: Application) :
    YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {


    override lateinit var debitCardSerialNumber: String
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()

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
            if (!isLoadMore.value!!)
                state.loading = true
            when (val response =
                transactionsRepository.getAccountTransactions(YAPApplication.homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    isLast.value = response.data.data.last
                    val transactionModelData: ArrayList<HomeTransactionListData> =
                        setUpSectionHeader(response)

                    if (isRefreshing.value!!) {
                        sortedCombinedTransactionList.clear()
                    }
                    isRefreshing.value = false

                    if (!sortedCombinedTransactionList.equals(transactionModelData)) {
                        sortedCombinedTransactionList.addAll(transactionModelData)
                    }

                    val unionList =
                        (sortedCombinedTransactionList.asSequence() + transactionModelData.asSequence())
                            .distinct()
                            .groupBy({ it.date })

                    for (lists in unionList.entries) {
                        if (lists.value.size > 1) {// sortedCombinedTransactionList.equals(transactionModelData fails in this case
                            var contentsList: ArrayList<Content> = arrayListOf()

                            for (transactionsDay in lists.value) {
                                contentsList.addAll(transactionsDay.content)

                            }

                            contentsList.sortByDescending { it ->
                                it.creationDate
                            }


                            var closingBalanceOfTheDay: Double = contentsList.get(0).balanceAfter
                            closingBalanceArray.add(closingBalanceOfTheDay)

                            var transactionModel: HomeTransactionListData = HomeTransactionListData(
                                "Type",
                                "AED",
                                /* transactionsDay.key!!*/
                                convertDate(contentsList.get(0).creationDate)!!,
                                contentsList.get(0).totalAmount.toString(),
                                contentsList.get(0).balanceAfter,
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
                            var numberstoReplace: Int = 0
                            var replaceNow: Boolean = false


                            val iterator = sortedCombinedTransactionList.iterator()
                            while (iterator.hasNext()) {
                                val item = iterator.next()
                                if (item.date.equals(convertDate(contentsList.get(0).creationDate))) {
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
//                    sortedCombinedTransactionList.sortBy { it ->  it.date  }

                    transactionsLiveData.value = sortedCombinedTransactionList
                    //if (isLoadMore.value!!)
                    isLoadMore.value = false
                    //transactionLogicHelper.transactionList = sortedCombinedTransactionList
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
        val contentList = response.data.data.content as ArrayList<Content>
        Collections.sort(contentList, object :
            Comparator<Content> {
            override fun compare(
                o1: Content,
                o2: Content
            ): Int {
                return o2.creationDate!!.compareTo(o1.creationDate!!)
            }
        })

        val groupByDate = contentList.groupBy { item ->
            convertDate(item.creationDate!!)
        }

        var transactionModelData: ArrayList<HomeTransactionListData> =
            arrayListOf()

        for (transactionsDay in groupByDate.entries) {


            var contentsList: ArrayList<Content> = arrayListOf()
            contentsList = transactionsDay.value as ArrayList<Content>
            contentsList.sortByDescending { it ->
                it.creationDate
            }

            var closingBalanceOfTheDay: Double = contentsList.get(0).balanceAfter
            closingBalanceArray.add(closingBalanceOfTheDay)

            var transactionModel: HomeTransactionListData = HomeTransactionListData(
                "Type",
                "AED",
                transactionsDay.key!!,
                contentsList.get(0).totalAmount.toString(),
                contentsList.get(0).balanceAfter,
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

//            transactionLogicHelper.transactionList = transactionModelData

//            transactionLogicHelper.transactionList =
//                transactionModelData
            MAX_CLOSING_BALANCE =
                closingBalanceArray.max()!!
        }
        return transactionModelData
    }

    override fun getDebitCards() {
        launch {
            state.loading = true
            when (val response = cardsRepository.getDebitCards("DEBIT")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.size != 0) {
                        debitCardSerialNumber = response.data.data[0].cardSerialNumber
                        clickEvent.setValue(EVENT_SET_CARD_PIN)
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }

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