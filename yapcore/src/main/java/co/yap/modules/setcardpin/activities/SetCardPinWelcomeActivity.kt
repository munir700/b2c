package co.yap.modules.setcardpin.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.R
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator


class SetCardPinWelcomeActivity : DefaultActivity(), INavigator, IFragmentHolder {

    var card: Card? = null

    companion object {
        private const val CARD = "card"
        fun newIntent(context: Context, card: Card?): Intent {
            val intent = Intent(context, SetCardPinWelcomeActivity::class.java)
            intent.putExtra(CARD, card)
            return intent
        }
    }

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@SetCardPinWelcomeActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_card_pin_welcome)
        setupData()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun setupData() {
        card = intent?.getValue(CARD, ExtraType.PARCEABLE.name) as? Card
        if (card == null) {
            showToast("Invalid card Serial number")
            finish()
        }
    }
}
