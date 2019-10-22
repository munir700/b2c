package co.yap.modules.dashboard.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.states.YapHomeState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.yapcore.SingleClickEvent
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


class YapHomeViewModel(application: Application) :
    YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {
    var contentList: ArrayList<HomeTransactionsResponse.HomeTransactionListData.Content> = arrayListOf()

    override lateinit var debitCardSerialNumber: String
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    override val transactionLogicHelper: TransactionLogicHelper =
        TransactionLogicHelper(context)

    private val cardsRepository: CardsRepository = CardsRepository
    private val transactionsRepository: TransactionsRepository = TransactionsRepository

    override fun onCreate() {
        super.onCreate()
        requestAccountTransactions()
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

        launch {
            state.loading = true

            var homeTransactionsRequest: HomeTransactionsRequest =
                HomeTransactionsRequest(1, 5, 10.00, 100.00, true, true, true)

            when (val response =
                transactionsRepository.getAccountTransactions(homeTransactionsRequest)) {
                is RetroApiResponse.Success -> {
                    Log.i(
                        "getAccountTransactions",
                        response.data.toString()
                    )
                    if (null != response.data.data) {
//                        transactionLogicHelper.transactionList = response.data.data.get(0)
                        val data: HomeTransactionsResponse.HomeTransactionListData = response.data.data
//                     val data:HomeTransactionsResponse.HomeTransactionListData=  response.data.data
//                        var contentList: List<HomeTransactionsResponse.HomeTransactionListData.Content> =
//                            response.data.data.content

//                        sort list by date

                        ////
                        loadJSONDummyList()
                        Collections.sort(contentList, object :
                            Comparator<HomeTransactionsResponse.HomeTransactionListData.Content> {
                            override fun compare(
                                o1: HomeTransactionsResponse.HomeTransactionListData.Content,
                                o2: HomeTransactionsResponse.HomeTransactionListData.Content
                            ): Int {
                                return o2.txnDate.compareTo(o1.txnDate)
                            }
                        })


                        transactionLogicHelper.transactioncontentList = contentList




                        val groupByDate = contentList.groupBy { item ->
                             convertDate(item.txnDate)
//                            item.txnDate
                        }

                        println(groupByDate.entries.joinToString(""))
//                        var transactionsMainList : TransactionsMainList =TransactionsMainList

                        for (content in groupByDate.entries) {
                            println(content.key)
                            println(content.value)

                        }
//                        Content
//                        var type: String,//txnType
//                        var totalAmountType: String,
//                        var date: String,//txnDate
//                        var totalAmount: String,// sum of txnAmount
//                        var closingBalance: String,//  closingBalance of last transaction of this day or the minimum 1
//                        var amountPercentage: Double,
//                        @Nullable var transactionItems: ArrayList<TransactionData>


//                        var sortedContentList : SortedContentList =SortedContentList


                        for (content in contentList) {

//                            if (content.txnDate){
//                            val date = convertDate(content.txnDate)


//                                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//                                parser.setTimeZone(TimeZone.getTimeZone("UTC"))
//                              val date= parser.parse(content.txnDate)

//                            }
                        }

                        ////////////////////////////////

                    }
                }

                is RetroApiResponse.Error -> {


                }

            }

            state.loading = false
        }
    }

    fun convertDate(txnDate: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        parser.setTimeZone(TimeZone.getTimeZone("UTC"))
        val convertedDate = parser.parse(txnDate)

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

        val newList: ArrayList<HomeTransactionsResponse.HomeTransactionListData.Content> = arrayListOf()

        val mainObj = JSONObject(loadTransactionFromJsonAssets(context))
        if (mainObj != null) {
            val mainDataList = mainObj.getJSONArray("content")
            if (mainDataList != null) {

                for (i in 0 until mainDataList!!.length()) {

                    val parentArrayList = mainDataList!!.getJSONObject(i)
                    val contect: HomeTransactionsResponse.HomeTransactionListData.Content =
                        HomeTransactionsResponse.HomeTransactionListData.Content(


                            parentArrayList.getString("closingBalance").toDouble(),
                            parentArrayList.getString("id").toInt(),
                            parentArrayList.getString("merchant"),
                            parentArrayList.getString("paymentMode"),
                            parentArrayList.getString("title"),
                            parentArrayList.getString("txnAmount").toDouble(),
                            parentArrayList.getString("txnCategory"),
                            parentArrayList.getString("txnCurrency"),
//                            this!!.convertDate(parentArrayList.getString("txnDate"))!!,
                            parentArrayList.getString("txnDate") ,
                            parentArrayList.getString("txnType")
                        )

//                    contentList.
                    newList.add(contect)

//                    transactioModelList.add(transactionModel)

                }
            }
        }
        contentList = newList


    }


}
