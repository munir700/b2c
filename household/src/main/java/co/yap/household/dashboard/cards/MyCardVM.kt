package co.yap.household.dashboard.cards

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.helpers.Utils
import com.google.gson.Gson
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MyCardVM @Inject constructor(override var state: IMyCard.State) :
    BaseRecyclerAdapterVM<Transaction, IMyCard.State>(), IMyCard.ViewModel {
    
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        addData(loadJSONDummyList())
    }

    private fun loadJSONDummyList(): ArrayList<Transaction> {
        val benefitsModelList: ArrayList<Transaction> = ArrayList<Transaction>()
        val mainObj = JSONObject(Utils.loadJsonFromAssets(context, "card_transactions.json"))
        val mainDataList = mainObj.getJSONObject("data")
        val content = mainDataList.getJSONArray("content")
        if (content != null) {
            for (i in 0 until content.length()) {
                val gson = Gson()
                val transactionData = gson.fromJson(content.getString(i), Transaction::class.java)
                benefitsModelList.add(transactionData)
            }
        }
        return benefitsModelList
    }
}
