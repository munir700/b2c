package co.yap.modules.forgotpasscode.activities

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.R
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot
import co.yap.yapcore.interfaces.IBaseNavigator

class ForgotPasscodeActivity : DefaultActivity(), INavigator, IFragmentHolder {
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@ForgotPasscodeActivity, R.id.main_nav_host_fragment)

    var preventTakeDeviceScreenShot: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_passcode)
        preventTakeDeviceScreenShot.observe(this, Observer {
            preventTakeScreenShot(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        preventTakeDeviceScreenShot.removeObservers(this)
    }

}