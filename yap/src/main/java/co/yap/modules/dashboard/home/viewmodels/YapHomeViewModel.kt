package co.yap.modules.dashboard.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.models.transactionsmodels.MianTransactionsList
import co.yap.modules.dashboard.home.states.YapHomeState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.SingleClickEvent
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


class YapHomeViewModel(application: Application) :
    YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {
    var contentList: ArrayList<Content> = arrayListOf()
    var closingBalanceArray: java.util.ArrayList<Double> = arrayListOf()

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

                        loadJSONDummyList()
                        Collections.sort(contentList, object :
                            Comparator<Content> {
                            override fun compare(
                                o1: Content,
                                o2: Content
                            ): Int {
                                return o2.txnDate.compareTo(o1.txnDate)
                            }
                        })

                        val groupByDate = contentList.groupBy { item ->
                            convertDate(item.txnDate)
//                            item.txnDate
                        }

                        println(groupByDate.entries.joinToString(""))

                        var TransactionModelData: java.util.ArrayList<MianTransactionsList> =
                            arrayListOf()

                        for (transactionsDay in groupByDate.entries) {


                            var contentsList: java.util.ArrayList<Content> = arrayListOf()
                            println(transactionsDay.key)
                            println(transactionsDay.value)
                            contentsList = transactionsDay.value as java.util.ArrayList<Content>
                            contentsList.sortByDescending { it ->
                                it.txnDate
                            }

                            var closingBalanceOfTheDay: Double = contentsList.get(0).closingBalance
                            closingBalanceArray.add(closingBalanceOfTheDay)
                            var calculateTotalAmount: Double = 0.0


                            for (contentValue in transactionsDay.value) {
                                calculateTotalAmount = calculateTotalAmount + contentValue.txnAmount
                                println(calculateTotalAmount)
                            }


                            var transactionModel: MianTransactionsList = MianTransactionsList(
                                "Type",
                                "AED",
                                transactionsDay.key!!,
                                calculateTotalAmount.toString(),
                                closingBalanceOfTheDay,
                                0.0 /*  "calculate the percentage as per formula from the keys".toDouble()*/,

                                contentsList

                            )
                            TransactionModelData.add(transactionModel)// this should be that main list


                        }


                    }
//

                    //                            calculateCummulativeClosingBalance(closingBalanceArray)

                }


                is RetroApiResponse.Error -> {


                }

            }

            state.loading = false
        }
    }
//
//    fun calculateCummulativeClosingBalance(closingBalanceArray:ArrayList<Double>) : Double {
////will count it in the end beacause we already kniw the current closing balance if rhe kast transactiuon in thr day
//        val maxClosingBalance = closingBalanceArray.max()
////        transactions closing balance of all the days from past
//
//        return
////        return closingBalanceArray.map { (0 / maxClosingBalance) * 100
//
//    }

//    func processDataSet(closingBalance: [Double]) : Double()
//    {
//                let maxClosingBalance = closingBalance.max()!
//                return dataSet.map { ($0 / max) * 100 }
//           
//    }


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

        val newList: ArrayList<Content> = arrayListOf()

        val mainObj = JSONObject(loadTransactionFromJsonAssets(context))
        if (mainObj != null) {
            val mainDataList = mainObj.getJSONArray("content")
            if (mainDataList != null) {

                for (i in 0 until mainDataList!!.length()) {

                    val parentArrayList = mainDataList!!.getJSONObject(i)
                    val contect: Content =
                        Content(
                            parentArrayList.getString("closingBalance").toDouble(),
                            parentArrayList.getString("id").toInt(),
                            parentArrayList.getString("merchant"),
                            parentArrayList.getString("paymentMode"),
                            parentArrayList.getString("title"),
                            parentArrayList.getString("txnAmount").toDouble(),
                            parentArrayList.getString("txnCategory"),
                            parentArrayList.getString("txnCurrency"),
//                          this!!.convertDate(parentArrayList.getString("txnDate"))!!,
                            parentArrayList.getString("txnDate"),
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
