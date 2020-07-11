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
import co.yap.networking.AppData
import co.yap.networking.RetroNetwork
import co.yap.networking.interfaces.NetworkConstraintsListener
import co.yap.security.AppSignature
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.EXTRA
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.NetworkConnectionManager
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.longToast
import co.yap.yapcore.initializeAdjustSdk
import com.crashlytics.android.Crashlytics
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.leanplum.Leanplum
import com.leanplum.LeanplumActivityHelper
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import java.util.*

class AAPApplication : ChatApplication(), NavigatorProvider {

    private external fun signatureKeysFromJNI(
        name: String,
        flavour: String,
        buildVariant: String,
        versionName: String,
        versionCode: String
    ): AppSignature

    init {
        System.loadLibrary("native-lib")
    }

    companion object {
        var originalSign: AppSignature? = null
    }

    override fun onCreate() {
        super.onCreate()
        initFireBase()
        originalSign =
            signatureKeysFromJNI(
                AppSignature::class.java.canonicalName?.replace(".", "/") ?: ""
                , BuildConfig.FLAVOR,
                BuildConfig.BUILD_TYPE,
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE.toString()
            )

        initNetworkLayer()
        setAppUniqueId(this)
        inItLeanPlum()
        initializeAdjustSdk(configManager?.adjustToken ?: "")
    }

    private fun initNetworkLayer() {
        RetroNetwork.initWith(this, getAppDataForNetwork())
        NetworkConnectionManager.init(this)

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

    private fun initFireBase() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .build()
            Fabric.with(fabric)
        }
    }

    private fun inItLeanPlum() {
        Leanplum.setApplicationContext(this)
        //Parser.parseVariables(this)
        LeanplumActivityHelper.enableLifecycleCallbacks(this)

        if (configManager?.isLiveRelease() == true) {
            Leanplum.setAppIdForProductionMode(
                configManager?.leanPlumSecretKey,
                configManager?.leanPlumKey
            )
        } else {
            Leanplum.setAppIdForDevelopmentMode(
                configManager?.leanPlumSecretKey,
                configManager?.leanPlumKey
            )
        }
        Leanplum.setIsTestModeEnabled(false)
        Leanplum.start(this)
    }

    private fun setAppUniqueId(context: Context) {
        var uuid: String?
        val sharedPrefs = SharedPreferenceManager(context)
        sharedPrefs.setThemeValue(Constants.THEME_YAP)
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

    private fun getAppDataForNetwork(): AppData {
        return AppData(
            flavor = configManager?.flavor ?: "",
            build_type = configManager?.buildType ?: "",
            baseUrl = "https://stg.yap.co/"
            //baseUrl = configManager?.baseUrl ?: ""
        )
    }
}
