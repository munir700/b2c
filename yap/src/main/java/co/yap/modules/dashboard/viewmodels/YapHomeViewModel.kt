package co.yap.modules.dashboard.viewmodels

import android.app.Application
import android.content.Context
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.models.TransactionModel
import co.yap.modules.dashboard.models.TransactionResponseDTO
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

    override fun loadJSONDummyList(): ArrayList<TransactionModel> {
        var transactionsList = TransactionResponseDTO(ArrayList())
        try {
            val jsonObject = JSONObject(loadTransactionFromJsonAssets(context))
            val jsonArray = jsonObject.getJSONArray("content")
            val arrayList:ArrayList<TransactionModel> = ArrayList<TransactionModel>()

            for (i in 0..(jsonArray.length() - 1)) {
                val jsonModel = jsonArray.getJSONObject(i)
                val transactionItem = TransactionModel(
                    jsonModel.getString("vendor"),
                    jsonModel.getDouble("amount"),
                    jsonModel.getDouble("amountPercentage"),
                    jsonModel.getString("type"),
                    jsonModel.getString("date"),
                    jsonModel.getString("time"),
                    jsonModel.getString("category"),
                    jsonModel.getString("currency")
                )
                arrayList.add(transactionItem)
            }
            transactionsList = TransactionResponseDTO(arrayList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return  transactionsList.arrayList
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