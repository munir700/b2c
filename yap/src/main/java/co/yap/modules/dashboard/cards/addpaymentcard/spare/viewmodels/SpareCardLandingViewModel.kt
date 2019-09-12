package co.yap.modules.dashboard.cards.addpaymentcard.viewmodels

import android.app.Application
import android.content.Context
import co.yap.modules.dashboard.cards.addpaymentcard.interfaces.ICards
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.modules.dashboard.cards.addpaymentcard.states.SpareCardLandingState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets


class SpareCardLandingViewModel(application: Application) :
    AddPaymentChildViewModel<ICards.State>(application), ICards.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: SpareCardLandingState =
        SpareCardLandingState()


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_spare_card_landing_display_text_title))
    }


    override fun loadJSONDummyList(): ArrayList<BenefitsModel> {
        val benefitsModelList: ArrayList<BenefitsModel> = ArrayList<BenefitsModel>()

        val mainObj = JSONObject(loadTransactionFromJsonAssets(context))
        if (mainObj != null) {
            val mainDataList = mainObj.getJSONArray("data")
            if (mainDataList != null) {

                for (i in 0 until mainDataList!!.length()) {
                    //
                    val parentArrayList = mainDataList!!.getJSONObject(i)
                    var benfitTitle: String = parentArrayList.getString("benfitTitle")


                    val benefitsModel: BenefitsModel = BenefitsModel(
                        benfitTitle
                    )

                    benefitsModelList.add(benefitsModel)

                }
            }
        }


        return benefitsModelList
    }

    private fun loadTransactionFromJsonAssets(context: Context): String? {
        val json: String?
        try {
            val `is` = context.assets.open("benefits.json")
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