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
import co.yap.yapcore.helpers.extentions.toast
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


            val intent = getIntent()
            val data: Uri? = intent.data
            Adjust.appWillOpenUrl(data, applicationContext)
            if (null != data) {

                toast(data.toString() + "MainActivity")
            }
            Log.v(" Adjust", "datais " + data)

//            getDataFromDeepLinkIntent()
        }

    }


    public override fun onResume() {
        super.onResume()
//
//        if (Adjust.isEnabled()) {
//            toast("Adjust.isEnabled()")
//        } else {
//            toast("Adjust.isdisabled()")
//        }
    }
//    2020-03-06 10:21:44.511 25426-25426/? I/FA: Install referrer extras are: adjust_reftag=cYsDahJoAisje&utm_source=yap_android
//    2020-03-06 10:21:44.757 2704-25521/? V/FA-SVC: InstallReferrer API result: adjust_reftag=cYsDahJoAisje&utm_source=yap_android
//    2020-03-06 10:21:44.790 25426-25448/? V/FA: InstallReferrer API result: adjust_reftag=cYsDahJoAisje&utm_source=yap_android
//    open fun onFireIntentClick() {
//        val intent =
//            Intent("com.android.vending.INSTALL_REFERRER")
//        intent.setPackage("co.yap.app")
//        intent.putExtra(
//            "referrer",
//            "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test"
//        )
//        sendBroadcast(intent)
//    }
 //    2020-03-06 12:41:46.460 8306-8306/? I/FA: Install referrer extras are: adjust_reftag=cdH1laBccPZ41&utm_source=yap_android
//    2020-03-06 12:41:46.855 2704-8407/? V/FA-SVC: InstallReferrer API result: adjust_reftag=cdH1laBccPZ41&utm_source=yap_android
//    2020-03-06 12:41:46.927 8306-8328/? V/FA: InstallReferrer API result: adjust_reftag=cdH1laBccPZ41&utm_source=yap_android
//    open fun onFireIntentClick() {
//        val intent =
//            Intent("com.android.vending.INSTALL_REFERRER")
//        intent.setPackage("co.yap.app")
//        intent.putExtra(
//            "referrer",
//            "inviter=abd123t"
//        )
//        intent.putExtra(
//            "inviter",
//            "abd123t333"
//        )
//        sendBroadcast(intent)
//    }


    private fun getDataFromDeepLinkIntent() {
        val intent = getIntent()
        val data: Uri? = intent.data
        Adjust.appWillOpenUrl(data, applicationContext)

        if (null != data) {
            val url = URL(
                data?.scheme,
                data?.host,
                data?.path
            ) // to retrive customer id from url placed in path
//            val customerId = data?.path
            val customerId = data.getQueryParameter("user_id");
            val date = DateFormat.format(
                "yyyy-MM-dd hh:mm:ss",
                Date()
            ) as String


            Log.i("url", url.toString())
            Log.i("urluserid", customerId.toString())
            Log.i(
                "urlDate",
                date.toString()
            )// this is the current dat & time when user is retriving this url on local app
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
//        getDataFromDeepLinkIntent()
        // data.toString() -> This is your deep_link parameter value.
    }
}
