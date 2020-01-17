package co.yap.app

import android.content.Context
import co.yap.networking.RetroNetwork
import co.yap.networking.interfaces.NetworkConstraintsListener
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.NetworkConnectionManager
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import com.crashlytics.android.Crashlytics
import com.leanplum.Leanplum
import com.leanplum.LeanplumActivityHelper
import com.leanplum.annotations.Parser
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.*


class AAPApplication : ChatApplication(BuildConfig.FLAVOR) {

    override fun onCreate() {
        super.onCreate()
        Utils.context = this

        initNetworkLayer()
        setAppUniqueId(this)
        initFirebase()
        inItLeanplum()
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
    private fun inItLeanplum() {
        Leanplum.setApplicationContext(this)
        Parser.parseVariables(this)

        LeanplumActivityHelper.enableLifecycleCallbacks(this)

        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode(
                "app_OjUbwCEcWfawOQzYABPyg5R7y9sFLgFm9C1JdgIa3Qk",
                "dev_2ssrA8Mh1BazUIZHqIQabRP0a76cQwZ1MYfHsJpODMQ"
            )
        } else {
            Leanplum.setAppIdForProductionMode(
                "app_OjUbwCEcWfawOQzYABPyg5R7y9sFLgFm9C1JdgIa3Qk",
                "prod_KX4ktWrg5iHyP12VbRZ92U0SOVXyYrcWk5B68TfBAW0"
            )
        }
        Leanplum.trackAllAppScreens()
        Leanplum.start(this)
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
}