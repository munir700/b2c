package co.yap.modules.dashboard.cards.paymentcarddetail.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.yap.R
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class ChangeCardPinActivity : DefaultActivity(), INavigator, IFragmentHolder {

    lateinit var cardSerialNumber : String
    var preventTakeDeviceScreenShot: MutableLiveData<Boolean> = MutableLiveData(false)

    companion object {
         const val CARD_SERIAL_NUMBER = "cardSerialNumber"
        fun newIntent(context: Context, cardSerialNumber: String): Intent {
            val intent = Intent(context, ChangeCardPinActivity::class.java)
            intent.putExtra(CARD_SERIAL_NUMBER, cardSerialNumber)
            return intent
        }
    }
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.change_pin_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_card_pin)
        setupData()
        preventTakeDeviceScreenShot.observe(this, Observer {
            preventTakeScreenShot(it)
        })
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.change_pin_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun setupData(){
        cardSerialNumber = intent.getStringExtra(CARD_SERIAL_NUMBER)
    }

    override fun onDestroy() {
        super.onDestroy()
        preventTakeDeviceScreenShot.removeObservers(this)
    }
}
