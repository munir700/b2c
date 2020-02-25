package co.yap.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import co.yap.app.modules.login.activities.VerifyPassCodePresenterActivity
import co.yap.household.onboard.otherscreens.InvalidEIDActivity
import co.yap.modules.dummy.ActivityNavigator
import co.yap.modules.dummy.NavigatorProvider
import co.yap.modules.others.helper.Constants.START_REQUEST_CODE
import co.yap.networking.RetroNetwork
import co.yap.networking.interfaces.NetworkConstraintsListener
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.EXTRA
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.helpers.*
import co.yap.yapcore.helpers.extentions.longToast
import co.yap.yapcore.initializeAdjustSdk
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
        BuildConfig.BUILD_TYPE, BuildConfig.BASE_URL, BuildConfig.ADJUST_APP_TOKEN
    )
), NavigatorProvider {

    override fun onCreate() {
        super.onCreate()
        if (!DeviceUtils().isDeviceRooted()) {
            SharedPreferenceManager(this).setThemeValue(Constants.THEME_YAP)
            initNetworkLayer()
            setAppUniqueId(this)
            initFirebase()
            initializeAdjustSdk()
        } else {
            //onTerminate()
        }
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

    private fun initFirebase() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .build()
            Fabric.with(fabric)
            inItLeanPlum()
        }
    }

    private fun inItLeanPlum() {
        Leanplum.setApplicationContext(this)
        //Parser.parseVariables(this)
        LeanplumActivityHelper.enableLifecycleCallbacks(this)

        val appId = BuildConfig.LEANPLUM_CLIENT_SECRET
        val devKey = BuildConfig.LEANPLUM_API_KEY

        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode(appId, devKey)
        } else {
            Leanplum.setAppIdForProductionMode(appId, devKey)
        }

        //Leanplum.setIsTestModeEnabled(true)
        Leanplum.start(this)
    }

    private fun setAppUniqueId(context: Context) {
        var uuid: String?
        val sharedPrefs = SharedPreferenceManager(context)
        uuid = sharedPrefs.getValueString(KEY_APP_UUID)
        if (uuid == null) {
            uuid = UUID.randomUUID().toString()
            sharedPrefs.save(KEY_APP_UUID, uuid)
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
                        InvalidEIDActivity::class.java
                    )
                )
            }

            override fun startVerifyPassCodePresenterActivity(
                activity: FragmentActivity, bundle: Bundle,
                completionHandler: ((resultCode: Int, data: Intent?) -> Unit)?
            ) {
                try {
                    val intent = Intent(activity, VerifyPassCodePresenterActivity::class.java)
                    intent.putExtra(EXTRA, bundle)
                    (activity as AppCompatActivity).startForResult(intent) { result ->
                        completionHandler?.invoke(result.resultCode, result.data)
                    }.onFailed { result ->
                        completionHandler?.invoke(result.resultCode, result.data)
                    }

                } catch (e: Exception) {
                    if (e is ClassNotFoundException) {
                        longToast("Something went wrong")
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