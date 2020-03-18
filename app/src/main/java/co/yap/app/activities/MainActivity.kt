package co.yap.app.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import co.yap.app.R
import co.yap.app.YAPApplication
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.DeviceUtils
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import co.yap.yapcore.referral.ReferralInfo
import com.adjust.sdk.Adjust

open class MainActivity : DefaultActivity(), IFragmentHolder, INavigator {

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@MainActivity,
            R.id.main_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YAPApplication.AUTO_RESTART_APP = false
        if (DeviceUtils().isDeviceRooted()) {
            showAlertDialogAndExitApp("This device is rooted. You can't use this app.")
        } else {
            setContentView(R.layout.activity_main)
            getDataFromDeepLinkIntent(intent)
        }
    }

    private fun getDataFromDeepLinkIntent(intent: Intent) {
        val data: Uri? = intent.data
        Adjust.appWillOpenUrl(data, applicationContext)
        data?.let { uri ->
            val customerId = uri.getQueryParameter("inviter")
            customerId?.let { cusId ->
                uri.getQueryParameter("time")?.let { time ->
                    val date = time.replace("_", " ")
                    SharedPreferenceManager(this).setReferralInfo(ReferralInfo(cusId, date))
                }
            }
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    fun onBackPressedDummy() {
        super.onBackPressed()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        getDataFromDeepLinkIntent(intent)
    }
}

