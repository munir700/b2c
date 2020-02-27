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
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import com.adjust.sdk.Adjust
 import java.net.URL


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

            val intent = getIntent()
            val data: Uri? = intent.data
            Adjust.appWillOpenUrl(data, applicationContext)

             if (null!=data){
                val url = URL(data?.scheme, data?.host, data?.path)
                 val customerId =  data?.path


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
        val data: Uri = intent.getData()
        // data.toString() -> This is your deep_link parameter value.
    }
}
