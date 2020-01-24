package co.yap.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import co.yap.app.modules.login.activities.VerifyPassCodePresenterActivity
import co.yap.modules.dummy.ActivityNavigator
import co.yap.modules.dummy.NavigatorProvider
import co.yap.modules.others.helper.Constants.START_REQUEST_CODE
import co.yap.networking.RetroNetwork
import co.yap.networking.interfaces.NetworkConstraintsListener
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.NetworkConnectionManager
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.longToast
import co.yap.yapcore.managers.AppCredentials
import com.crashlytics.android.Crashlytics
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.leanplum.Leanplum
import com.leanplum.LeanplumActivityHelper
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.*


class AAPApplication : ChatApplication(BuildConfig.FLAVOR), NavigatorProvider {

    override fun onCreate() {
        super.onCreate()
        Utils.context = this

        initNetworkLayer()
        setAppUniqueId(this)
        initFirebase()
        inItLeanPlum()
    }

    private fun initNetworkLayer() {
        RetroNetwork.initWith(this, BuildConfig.BASE_URL)
        NetworkConnectionManager.init(this)
        setAppUniqueId(this)

        RetroNetwork.listenNetworkConstraints(object : NetworkConstraintsListener {
            override fun onInternetUnavailable() {
            }

            override fun onCacheUnavailable() {
            }

            override fun onSessionInvalid() {
                AuthUtils.navigateToSoftLogin(applicationContext)
            }
        })
    }


    /**
     * In this function initialize Firebase.
     */

    private fun initFirebase() {
        val fabric = Fabric.Builder(this)
            .kits(Crashlytics())
            .debuggable(BuildConfig.DEBUG) // Enables Crashlytics debugger
            .build()
        Fabric.with(fabric)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

    }

    /**
     * In this function initialize Leanplum.
     */
    private fun inItLeanPlum() {

        Leanplum.setApplicationContext(this)
        //Parser.parseVariables(this)
        LeanplumActivityHelper.enableLifecycleCallbacks(this)

        val appId = AppCredentials.appId
        val devKey = AppCredentials.devKey
        val prodKey = AppCredentials.prodKey

        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode(appId, devKey)
        } else {
            Leanplum.setAppIdForProductionMode(appId, prodKey)
        }

        //Leanplum.trackAllAppScreens()
        Leanplum.start(this)
        Leanplum.enableTestMode()
    }

    private fun setAppUniqueId(context: Context) {
        var uuid: String?
        val sharedPrefs = SharedPreferenceManager(context)
        uuid = sharedPrefs.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        if (uuid == null) {
            uuid = UUID.randomUUID().toString()
            sharedPrefs.save(SharedPreferenceManager.KEY_APP_UUID, uuid)
        }
    }

    override fun onTerminate() {
        NetworkConnectionManager.destroy(this)
        super.onTerminate()
    }

    override fun provideNavigator(): ActivityNavigator {
        return object : ActivityNavigator {
            override fun startEIDNotAcceptedActivity(activity: FragmentActivity) {
            }

            override fun startVerifyPassCodePresenterActivity(
                activity: FragmentActivity,
                completionHandler: ((resultCode: Int, data: Intent?) -> Unit)?
            ) {
                try {
                    val intent = Intent(activity, VerifyPassCodePresenterActivity::class.java)
                    (activity as AppCompatActivity).startForResult(intent) { result ->
                        completionHandler?.invoke(result.resultCode, result.data)
                    }.onFailed { result ->
                        completionHandler?.invoke(result.resultCode, result.data)
                    }

                } catch (e: Exception) {
                    if (e is ClassNotFoundException) {
                        longToast(
                            "InlineActivityResult library not installed falling back to default method, please install \" +\n" +
                                    "\"it from https://github.com/florent37/InlineActivityResult if you want to get inline activity results."
                        )
                        activity.startActivityForResult(
                            Intent(activity, VerifyPassCodePresenterActivity::class.java),
                            START_REQUEST_CODE
                        )
                    }
                }

            }
        }
    }
}