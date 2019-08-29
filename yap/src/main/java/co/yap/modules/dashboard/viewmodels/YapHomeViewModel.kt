package co.yap.modules.dashboard.viewmodels

import android.app.Application
import android.content.Context
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.models.Transaction
import co.yap.modules.dashboard.models.TransactionModel
import co.yap.modules.dashboard.states.YapHomeState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets


class YapHomeViewModel(application: Application) : BaseViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    val maxTransactionVal: Int = 600


    override fun loadJSONDummyList(): ArrayList<TransactionModel> {

        val transactioModelList: ArrayList<TransactionModel> = ArrayList<TransactionModel>()

        val mainObj = JSONObject(loadTransactionFromJsonAssets(context))
        if (mainObj != null) {
            val mainDataList = mainObj.getJSONArray("data")
            if (mainDataList != null) {

                for (i in 0 until mainDataList!!.length()) {

                    val parentArrayList = mainDataList!!.getJSONObject(i)
                    var date: String = parentArrayList.getString("date")
                    var totalAmount: String = parentArrayList.getString("totalAmount")
                    var closingBalance: String = parentArrayList.getString("closingBalance")
                    var amountPercentage: String = parentArrayList.getString("amountPercentage")

                    val childArrayList = parentArrayList!!.getJSONArray("transaction")
                    val transactionsArrayList: ArrayList<Transaction> = ArrayList<Transaction>()

                    for (j in 0 until childArrayList!!.length()) {
                        val innerElem = childArrayList!!.getJSONObject(j)
                        val itemType = "ITEM"
//                        val itemType = innerElem.getString("type")
                        val vendor = innerElem.getString("vendor")
                        val imageUrl = innerElem.getString("imageUrl")
                        val time = innerElem.getString("time")
                        val category = innerElem.getString("category")
                        val amount = innerElem.getString("amount")
                        val currency = innerElem.getString("currency")
                        val transaction: Transaction = Transaction(
                            itemType,
                            vendor,
                            imageUrl,
                            time,
                            category,
                            amount,
                            currency
                        )
                        transactionsArrayList.add(transaction)
                    }

                    val transactionModel: TransactionModel = TransactionModel(
                        "HEADER",
                        date,
                        totalAmount,
                        closingBalance,
                        amountPercentage,
                        transactionsArrayList
                    )
                    transactioModelList.add(transactionModel)

                }
            }
        }

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

    fun getAmountPercentage(dataSet: Double): Double {

        val percentage = dataSet / maxTransactionVal * 100
        return Math.round(percentage).toDouble()
    }
}