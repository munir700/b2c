package co.yap.modules.dashboard.yapit.topup.main.topupamount.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.customers.responsedtos.beneficiary.TopUpTransactionModel
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class TopUpCardActivity : DefaultActivity(), INavigator, IFragmentHolder {

    var cardInfo: TopUpCard? = null
    var topUpTransactionModel: MutableLiveData<TopUpTransactionModel>? = MutableLiveData()
    var orderId: String? = null
    var sessionId: String? = null

    companion object {
        fun newIntent(context: Context, card: TopUpCard): Intent {
            val intent = Intent(context, TopUpCardActivity::class.java)
            intent.putExtra(Constants.CARD, card)
            return intent
        }
    }


    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.card_top_up_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up_card)
        if (intent.hasExtra(Constants.CARD)) {
            val card: Parcelable = intent.getParcelableExtra(Constants.CARD)
            if (card is TopUpCard) {
                cardInfo = card
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}