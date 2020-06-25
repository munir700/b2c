package co.yap.modules.dashboard.yapit.topup.topupamount.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.customers.responsedtos.beneficiary.TopUpTransactionModel
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.IBaseNavigator

class TopUpCardActivity : DefaultActivity(), INavigator, IFragmentHolder {

    var cardInfo: TopUpCard? = null
    var topUpTransactionModel: MutableLiveData<TopUpTransactionModel>? = MutableLiveData()
    var successButtonLabel: String = ""

    companion object {
        fun newIntent(
            context: Context,
            card: TopUpCard,
            successButtonLabel: String
        ): Intent {
            val intent = Intent(context, TopUpCardActivity::class.java)
            intent.putExtra(Constants.CARD, card)
            intent.putExtra("successButtonLabel", successButtonLabel)
            return intent
        }
    }

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.card_top_up_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up_card)
        cardInfo =
            (intent?.getValue(Constants.CARD, ExtraType.PARCEABLE.name) as? TopUpCard)
        successButtonLabel =
            (intent?.getValue("successButtonLabel", ExtraType.STRING.name) as? String) ?: ""
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}