package co.yap.yapcore

import android.app.Activity
import android.app.Application
import android.net.UrlQuerySanitizer
import android.os.Bundle
import co.yap.yapcore.adjust.ReferralInfo
import co.yap.yapcore.config.BuildConfigManager
import co.yap.yapcore.constants.Constants.KEY_COUNTRY_CODE
import co.yap.yapcore.constants.Constants.REFERRAL_COUNTRY_ISO_CODE
import co.yap.yapcore.constants.Constants.REFERRAL_ID
import co.yap.yapcore.constants.Constants.REFERRAL_TIME
import co.yap.yapcore.enums.ProductFlavour
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.SessionManager
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.AdjustEvent
import com.adjust.sdk.LogLevel


/*
* Following sdk's included
* -> Adjust SDK
* */

fun Application.initializeAdjustSdk(configManager: BuildConfigManager?) {

    configManager?.let { configurations ->
        val config = AdjustConfig(
            this,
            configurations.adjustToken,
            if (configurations.isReleaseBuild()) AdjustConfig.ENVIRONMENT_PRODUCTION else AdjustConfig.ENVIRONMENT_SANDBOX
        )

        when (configurations.flavor) {
            ProductFlavour.PROD.flavour -> {
                Adjust.setEnabled(true)
                config.setAppSecret(3, 1746894148, 2040383572, 1770588342, 2016748378)
                config.setDefaultTracker("n44w5ee")
                config.setEventBufferingEnabled(true)
                config.setPreinstallTrackingEnabled(true)
            }
            ProductFlavour.PREPROD.flavour -> {
                Adjust.setEnabled(true)
                config.setEventBufferingEnabled(true)
                config.setPreinstallTrackingEnabled(true)
                config.setAppSecret(1, 82588340, 60633897, 806753301, 962146915)
            }
            ProductFlavour.STG.flavour -> {
                config.setAppSecret(1, 1236756048, 110233912, 2039250280, 199413548)
            }
            ProductFlavour.INTERNAL.flavour -> {
                config.setAppSecret(1, 1236756048, 110233912, 2039250280, 199413548)
            }
            ProductFlavour.QA.flavour -> {
            }
            ProductFlavour.DEV.flavour -> {
            }
            else -> throw IllegalStateException("Invalid build flavour found ${configurations.flavor}")
        }


        if (!configurations.isReleaseBuild()) config.setLogLevel(LogLevel.VERBOSE)
        config.setSendInBackground(true)
        config.setOnEventTrackingSucceededListener {}
        config.setOnEventTrackingFailedListener { }
        config.setOnSessionTrackingSucceededListener { }
        config.setOnSessionTrackingFailedListener { }
        config.setOnDeeplinkResponseListener { deepLink ->
            deepLink?.let { uri ->
                val sharedPref = SharedPreferenceManager.getInstance(this)
                val customerId = UrlQuerySanitizer(uri.toString()).getValue(REFERRAL_ID)
                val time = UrlQuerySanitizer(uri.toString()).getValue(REFERRAL_TIME)
                val countryISOCode = UrlQuerySanitizer(uri.toString()).getValue(KEY_COUNTRY_CODE)
                sharedPref.setReferralInfo(ReferralInfo(customerId, time))
                sharedPref.save(REFERRAL_COUNTRY_ISO_CODE, countryISOCode)
            }
            false
        }
        config.setOnAttributionChangedListener { attribution ->
        }

        Adjust.onCreate(config)
//        Adjust.addSessionPartnerParameter("account_id", SessionManager.user?.currentCustomer?.customerId)
        registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
        config.setOnAttributionChangedListener { }
    }
}

private class AdjustLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityResumed(activity: Activity) {
        Adjust.onResume()
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityPaused(activity: Activity) {
        Adjust.onPause()
    }
}

fun fireAdjustEvent(event: String) {
    val attribution = Adjust.getAttribution()
    val adjustEvent = AdjustEvent(event)
    adjustEvent.setCallbackId(SessionManager.user?.currentCustomer?.customerId)
    adjustEvent.addCallbackParameter("account_id", SessionManager.user?.currentCustomer?.customerId)
    Adjust.trackEvent(adjustEvent)
}

class AdjustEvents {
    companion object {
        fun trackAdjustPlatformEvent(eventName: String, value: String = "") {
            fireAdjustEvent(eventName)

        }
    }
}
