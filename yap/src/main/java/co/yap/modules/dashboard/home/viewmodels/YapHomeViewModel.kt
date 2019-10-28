package co.yap.modules.dashboard.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.states.YapHomeState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.modules.dashboard.store.paging.transactions.TransactionsDataSource
import co.yap.modules.dashboard.store.paging.transactions.TransactionsDataSourceFactory
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Pageable
import co.yap.networking.transactions.responsedtos.transaction.Sort
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


class YapHomeViewModel(application: Application) :
    YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {


    override var MAX_CLOSING_BALANCE: Double = 0.0
    //    var contentList: ArrayList<Content> = arrayListOf()
    var closingBalanceArray: java.util.ArrayList<Double> = arrayListOf()

    override lateinit var debitCardSerialNumber: String
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    override val transactionLogicHelper: TransactionLogicHelper =
        TransactionLogicHelper(context)

    private val cardsRepository: CardsRepository = CardsRepository
    private val transactionsRepository: TransactionsRepository = TransactionsRepository

    private lateinit var storeSourceFactory: TransactionsDataSourceFactory
    var contentList: ArrayList<Content> = arrayListOf()

    override lateinit var storesLiveData: LiveData<PagedList<HomeTransactionListData>>

//    init {
//        setUpTransactionsRepo()
//
//    }

    fun setUpTransactionsRepo() {
        storeSourceFactory = TransactionsDataSourceFactory(transactionsRepository, this)
        storesLiveData = LivePagedListBuilder(storeSourceFactory, getPagingConfigs()).build()

    }

    private fun getPagingConfigs(): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(30 * 2)
            .setEnablePlaceholders(false)
            .build()
    }

    override fun listIsEmpty(): Boolean {
        return storesLiveData.value?.isEmpty() ?: true
    }

    override fun retry() {
//        storeSourceFactory.storeDataSourceLiveData.value?.retry()
        setUpTransactionsRepo()

    }

    override fun getState(): LiveData<PagingState> =
        Transformations.switchMap<TransactionsDataSource,
                PagingState>(
            storeSourceFactory.storeDataSourceLiveData,
            TransactionsDataSource::state
        )

    override fun onCreate() {
        super.onCreate()
//        setUpTransactionsRepo()
//        requestAccountTransactions()
        setUpTransactionsRepo()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)

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


    fun requestAccountTransactions() {


//need to make this page count request dynamic in pagination as per page

        var homeTransactionsRequest: HomeTransactionsRequest =
            HomeTransactionsRequest(1, 40, 0.00, 200000.00, true, true, true)

        launch {
            state.loading = true

            when (val response =
                transactionsRepository.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {

                    Log.i("getAccountTransactions", response.data.toString())

                    if (null != response.data.data) {
                        contentList = response.data.data.content as ArrayList<Content>
//                        loadJSONDummyList()
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

                        println(groupByDate.entries.joinToString(""))

                        var transactionModelData: java.util.ArrayList<HomeTransactionListData> =
                            arrayListOf()

                        for (transactionsDay in groupByDate.entries) {


                            var contentsList: java.util.ArrayList<Content> = arrayListOf()
                            println(transactionsDay.key)
                            println(transactionsDay.value)
                            contentsList = transactionsDay.value as java.util.ArrayList<Content>
                            contentsList.sortByDescending { it ->
                                it.creationDate
                            }

                            var closingBalanceOfTheDay: Double = contentsList.get(0).balanceAfter
                            closingBalanceArray.add(closingBalanceOfTheDay)
//                            var calculateTotalAmount: Double = 0.0
//                            for (contentValue in transactionsDay.value) {
//                                calculateTotalAmount = calculateTotalAmount + contentValue.amount
//                                println(calculateTotalAmount)
//                            }

                            var transactionModel: HomeTransactionListData = HomeTransactionListData(
                                "Type",
                                "AED",
                                transactionsDay.key!!,
                                contentsList.get(0).totalAmount.toString(),
                                contentsList.get(0).balanceAfter,
                                80.00 /*  "calculate the percentage as per formula from the keys".toDouble()*/,
                                contentsList,

                                //

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

                            transactionLogicHelper.transactionList = transactionModelData
                            MAX_CLOSING_BALANCE = closingBalanceArray.max()!!
                        }
                    }
                }

                is RetroApiResponse.Error -> {

                }
            }

            state.loading = false
        }
    }

    fun convertDate(creationDate: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        parser.setTimeZone(TimeZone.getTimeZone("UTC"))
        val convertedDate = parser.parse(creationDate)

        val pattern = "MMMM dd, yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.format(convertedDate)

        return date
    }

    private fun loadTransactionFromJsonAssets(context: Context): String? {
        val json: String?
        try {
            val `is` = context.assets.open("paginatedTransactions.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun loadJSONDummyList() {

        val newList: ArrayList<Content> = arrayListOf()

        val mainObj = JSONObject(loadTransactionFromJsonAssets(context))
        if (mainObj != null) {
            val mainDataList = mainObj.getJSONArray("content")
            if (mainDataList != null) {

                for (i in 0 until mainDataList!!.length()) {

                    val parentArrayList = mainDataList!!.getJSONObject(i)

                    val contect: Content =
                        Content(
                            "accountUuid1",
                            parentArrayList.getDouble("amount"),
                            parentArrayList.getDouble("balanceAfter"),
                            10.0,
                            "1000000000168",
                            "TRANSACTION",
                            "111",
                            "111",
                            parentArrayList.getString("txnCurrency"),
                            "3000000000098",
                            "test",
                            "AE070333000000000120068",
                            "YAP",
                            parentArrayList.getString("paymentMode"),
                            "CR050819073136236111",
                            "CD",
                            "B2C_IBAN_ACCOUNT_HOLDER",
                            "Approved Content",
                            parentArrayList.getString("senderName"),
                            "SUCCESS",
                            parentArrayList.getDouble("amount"),
                            "728172817281",
                            "6666",
                            parentArrayList.getString("txnType"),
                            "111",
                            parentArrayList.getString("updatedDate"),
                            "B2C_ACCOUNT"
                        )

                    newList.add(contect)
                }
            }
        }
        contentList = newList
    }
}