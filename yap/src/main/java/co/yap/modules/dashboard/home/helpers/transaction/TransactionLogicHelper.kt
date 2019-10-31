package co.yap.modules.dashboard.helpers.transaction

import android.content.Context
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import java.io.IOException
import java.nio.charset.StandardCharsets

class TransactionLogicHelper(
    val context: Context
) {
    var transactionList: MutableList<HomeTransactionListData> = mutableListOf()

//    var transactionList: ArrayList<HomeTransactionListData> = arrayListOf()
//    var transactioncontentList: List<Content> = arrayListOf()

    init {
//        transactionList = loadJSONDummyList()
//        HomeTransactionListData
//    var contentList: List<HomeTransactionsResponse.HomeTransactionListData.Content>

//   now structure that lis data
    }
//    val transactioModelList: ArrayList<HomeTransactionListData> = ArrayList<HomeTransactionListData>()

    private fun loadJSONDummyList(): ArrayList<HomeTransactionListData> {
        val transactioModelList: ArrayList<HomeTransactionListData> =
            ArrayList<HomeTransactionListData>()

//        val mainObj = JSONObject(loadTransactionFromJsonAssets(context))
//        if (mainObj != null) {
//            val mainDataList = mainObj.getJSONArray("data")
//            if (mainDataList != null) {
//
//                for (i in 0 until mainDataList!!.length()) {
//
//                    val parentArrayList = mainDataList!!.getJSONObject(i)
//                    var date: String = parentArrayList.getString("date")
//                    var totalAmount: String = parentArrayList.getString("totalAmount")
//                    var totalAmountType: String = parentArrayList.getString("totalAmountType")
//                    var closingBalance: String = parentArrayList.getString("closingBalance")
//                    var amountPercentage: Double = parentArrayList.getDouble("amountPercentage")
//
//                    val childArrayList = parentArrayList!!.getJSONArray("content")
//                    val transactionsArrayList: ArrayList<Content> = ArrayList<Content>()
//
//                    for (j in 0 until childArrayList!!.length()) {
//                        val innerElem = childArrayList!!.getJSONObject(j)
//                        val itemType = "ITEM"
////                      val itemType = innerElem.getString("txnType")
//                        val type = innerElem.getString("txnType")
//                        val vendor = innerElem.getString("senderName")
//                        val imageUrl = innerElem.getString("imageUrl")
//                        val time = innerElem.getString("updatedDate")
//                        val category = innerElem.getString("category")
//                        val amount = innerElem.getString("amount")
//                        val currency = innerElem.getString("currency")
//                        val transaction: Content = Content(
//                            itemType,
//                            type,
//                            vendor,
//                            imageUrl,
//                            time,
//                            category,
//                            amount,
//                            currency
//                        )
//                        transactionsArrayList.add(transaction)
//                    }
//
//                    val transactionModel: HomeTransactionListData = HomeTransactionListData(
//                        "HEADER",
//                        totalAmountType,
//                        date,
//                        totalAmount,
//                        closingBalance,
//                        amountPercentage,
//                        transactionsArrayList
//                    )
//                    transactioModelList.add(transactionModel)
//
//                }
//            }
//        }
//        Collections.sort(transactioModelList, object : Comparator<HomeTransactionListData> {
//            override fun compare(o1: HomeTransactionListData, o2: HomeTransactionListData): Int {
//                return o2.date.compareTo(o1.date)
//            }
//        })

        return transactioModelList
    }

    private fun loadTransactionFromJsonAssets(context: Context): String? {
        val json: String?
        try {
            val `is` = context.assets.open("transactions.json")
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

}