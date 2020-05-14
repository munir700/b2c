package co.yap.household.dashboard.cards

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.helpers.Utils
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject

class MyCardVM @Inject constructor(override var state: IMyCard.State) :
    BaseRecyclerAdapterVM<Content, IMyCard.State>(), IMyCard.ViewModel {
    
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        addData(loadJSONDummyList())
    }

    private fun loadJSONDummyList(): ArrayList<Content> {
        val benefitsModelList: ArrayList<Content> = ArrayList<Content>()
        val mainObj = JSONObject(Utils.loadJsonFromAssets(context, "card_transactions.json"))
        val mainDataList = mainObj.getJSONObject("data")
        val content = mainDataList.getJSONArray("content")
        if (content != null) {
            for (i in 0 until content.length()) {
                val gson = Gson()
                val transactionData = gson.fromJson(content.getString(i), Content::class.java)
                benefitsModelList.add(transactionData)
            }
        }
        return benefitsModelList
    }
}