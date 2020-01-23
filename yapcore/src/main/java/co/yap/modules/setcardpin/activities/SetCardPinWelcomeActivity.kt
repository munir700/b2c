package co.yap.modules.setcardpin.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.R
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator


class SetCardPinWelcomeActivity : DefaultActivity(), INavigator, IFragmentHolder {

    var cardSerialNumber: String? = null
    var preventTakeDeviceScreenShot: MutableLiveData<Boolean> = MutableLiveData()

    companion object {
        private const val CARD_SERIAL_NUMBER = "cardSerialNumber"
        fun newIntent(context: Context, cardSerialNumber: String): Intent {
            val intent = Intent(context, SetCardPinWelcomeActivity::class.java)
            intent.putExtra(CARD_SERIAL_NUMBER, cardSerialNumber)
            return intent
        }
    }

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@SetCardPinWelcomeActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_card_pin_welcome)
        setupData()
        preventTakeDeviceScreenShot.observe(this, Observer {
            preventTakeScreenShot(it)
        })
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun setupData() {
        if (intent != null && intent.hasExtra(CARD_SERIAL_NUMBER)) {
            cardSerialNumber = intent.getStringExtra(CARD_SERIAL_NUMBER)
        } else {
            showToast("Invalid card Serial number")
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        preventTakeDeviceScreenShot.removeObservers(this)
    }
}
