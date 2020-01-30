package co.yap.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import co.yap.app.modules.login.activities.VerifyPassCodePresenterActivity
import co.yap.household.onboarding.onboarding.activities.EIDNotAcceptedActivity
import co.yap.modules.dummy.ActivityNavigator
import co.yap.modules.dummy.NavigatorProvider
import co.yap.modules.others.helper.Constants.START_REQUEST_CODE
import co.yap.networking.RetroNetwork
import co.yap.networking.interfaces.NetworkConstraintsListener
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.AppInfo
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.NetworkConnectionManager
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.longToast
import com.crashlytics.android.Crashlytics
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.leanplum.Leanplum
import com.leanplum.LeanplumActivityHelper
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.*


class AAPApplication : ChatApplication(
    AppInfo(
        BuildConfig.VERSION_NAME,
        BuildConfig.VERSION_CODE,
        BuildConfig.FLAVOR,
        BuildConfig.BUILD_TYPE
    )
), NavigatorProvider {

    override fun onCreate() {
        super.onCreate()
        SharedPreferenceManager(this).setThemeValue(Constants.THEME_YAP)
        initCrashLytics()
        initDebugTreeTimber()
        initNetworkLayer()
        setAppUniqueId(this)
        initFirebase()
        inItLeanPlum()
        createChannel(packageName, "Default")
    }

    private fun initDebugTreeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }


    private fun initCrashLytics() {
        val fabric = Fabric.Builder(this)
            .kits(Crashlytics())
            .debuggable(BuildConfig.DEBUG) // Enables Crashlytics debugger
            .build()
        Fabric.with(fabric)
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

        val appId = BuildConfig.LEANPLUM_CLIENT_SECRET
        val devKey = BuildConfig.LEANPLUM_API_KEY
        //val prodKey = AppCredentials.prodKey

        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode(appId, devKey)
        } else {
            Leanplum.setAppIdForProductionMode(appId, devKey)
        }

//        Leanplum.setIsTestModeEnabled(true)
        Leanplum.start(this)
    }

    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )// TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Default channel"
            //getString(R.string.app_name)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        // TODO: Step 1.6 END create a channel
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

                activity.startActivity(
                    Intent(
                        activity,
                        EIDNotAcceptedActivity::class.java
                    )
                )
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
                            "Something went wrong"
                            //"InlineActivityResult library not installed falling back to default method, please install \" +\n" +
                            //        "\"it from https://github.com/florent37/InlineActivityResult if you want to get inline activity results."
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