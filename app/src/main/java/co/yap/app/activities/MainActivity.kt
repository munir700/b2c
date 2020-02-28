package co.yap.app.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import co.yap.app.R
import co.yap.app.YAPApplication
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.DeviceUtils
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import com.adjust.sdk.Adjust
import java.net.URL
import java.util.*


open class MainActivity : DefaultActivity(), IFragmentHolder, INavigator {

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@MainActivity,
            R.id.main_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DeviceUtils().isDeviceRooted()) {
            showAlertDialogAndExitApp("This device is rooted. You can't use this app.")
        } else {
            YAPApplication.AUTO_RESTART_APP = false
            setContentView(R.layout.activity_main)

            getDataFromDeepLinkIntent()
        }

    }

    private fun getDataFromDeepLinkIntent() {
        val intent = getIntent()
        val data: Uri? = intent.data
        Adjust.appWillOpenUrl(data, applicationContext)

        if (null != data) {
            val url = URL(
                data?.scheme,
                data?.host,
                data?.path
            )// to retrive customer id from url placed in path
            val customerId = data?.path
            val date = DateFormat.format(
                "yyyy-MM-dd hh:mm:ss",
                Date()
            ) as String

            Log.i("abc", date.toString())
            Log.i("abcurl",url.toString())
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
        val data: Uri = intent.getData()
        getDataFromDeepLinkIntent()
        // data.toString() -> This is your deep_link parameter value.
    }
}
