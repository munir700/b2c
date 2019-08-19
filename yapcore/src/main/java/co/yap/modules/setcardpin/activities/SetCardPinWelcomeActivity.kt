package co.yap.modules.setcardpin.activities

import android.os.Bundle
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.R
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator


class SetCardPinWelcomeActivity : DefaultActivity(), INavigator, IFragmentHolder {

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@SetCardPinWelcomeActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_card_pin_welcome)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}
