package co.yap.modules.dashboard.cards.paymentcarddetail.activities

import android.os.Bundle
import co.yap.R
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class ChangeCardPinActivity : DefaultActivity(), INavigator, IFragmentHolder {
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.change_pin_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_card_pin)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.change_pin_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}