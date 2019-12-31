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


        initFirebase()
        inItLeanplum()
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

        /*TODO: Need Leanplum API key*/
        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode("Your App ID", "Your Development Key")
        } else {
            Leanplum.setAppIdForProductionMode("Your App ID", "Your Production Key")
        }

        /*TODO: Tracks all screens in your app as states in Leanplum.*/
        Leanplum.trackAllAppScreens()

        /*TODO: This will only run once per session, even if the activity is restarted. */
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