package co.yap.modules.dashboard.viewmodels

import android.app.Application
import android.content.Context
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.models.TransactionModel
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

//            when (val response =
//                transactionsRepository.getAccountTransactions(homeTransactionsRequest)) {
//                is RetroApiResponse.Success -> {
//                    Log.i(
//                        "getAccountTransactions",
//                        response.data.toString()
//                    )
//                    if (null != response.data.data) {

            loadJSONDummyList()
            Collections.sort(contentList, object :
                Comparator<Content> {
                override fun compare(
                    o1: Content,
                    o2: Content
                ): Int {
                    return o2.updatedDate.compareTo(o1.updatedDate)
                }
            })

            val groupByDate = contentList.groupBy { item ->
                convertDate(item.updatedDate)
//                            item.updatedDate
            }

            println(groupByDate.entries.joinToString(""))

            var transactionModelData: java.util.ArrayList<TransactionModel> =
                arrayListOf()

            for (transactionsDay in groupByDate.entries) {


                var contentsList: java.util.ArrayList<Content> = arrayListOf()
                println(transactionsDay.key)
                println(transactionsDay.value)
                contentsList = transactionsDay.value as java.util.ArrayList<Content>
                contentsList.sortByDescending { it ->
                    it.updatedDate
                }

                var closingBalanceOfTheDay: Double = contentsList.get(0).balanceAfter
                closingBalanceArray.add(closingBalanceOfTheDay)
//                            var calculateTotalAmount: Double = 0.0
//
//                            for (contentValue in transactionsDay.value) {
//                                calculateTotalAmount = calculateTotalAmount + contentValue.amount
//                                println(calculateTotalAmount)
//                            }


                var transactionModel: TransactionModel = TransactionModel(
                    "Type",
                    "AED",
                    transactionsDay.key!!,
                    contentsList.get(0).totalAmount.toString(),
                    contentsList.get(0).balanceAfter,
                    0.0 /*  "calculate the percentage as per formula from the keys".toDouble()*/,

                    contentsList

                )
                transactionModelData.add(transactionModel)// this should be that main list

                transactionLogicHelper.transactionList = transactionModelData
            }
        }
        //                            calculateCummulativeClosingBalance(closingBalanceArray)
//                }

//                is RetroApiResponse.Error -> {
//
//
//                }
//
//            }
//
//            state.loading = false
//        }
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

//    func processDataSet(balanceAfter: [Double]) : Double()
//    {
//                let maxClosingBalance = balanceAfter.max()!
//                return dataSet.map { ($0 / max) * 100 }
//           
//    }


    fun convertDate(updatedDate: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        parser.setTimeZone(TimeZone.getTimeZone("UTC"))
        val convertedDate = parser.parse(updatedDate)

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

//                    transactioModelList.add(transactionModel)
                }
            }
        }

        contentList = newList


    }


}
