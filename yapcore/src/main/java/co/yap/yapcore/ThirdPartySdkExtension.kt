package co.yap.yapcore

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.AdjustEvent
import com.adjust.sdk.LogLevel

/*
* Following sdk's included
* -> Adjust SDK
* */

fun Application.initializeAdjustSdk(appToken: String) {

    val environment = AdjustConfig.ENVIRONMENT_SANDBOX //AdjustConfig.ENVIRONMENT_PRODUCTION
    val config = AdjustConfig(this, appToken, environment)
    config.setAppSecret(1, 1236756048, 110233912, 2039250280, 199413548)
    config.setLogLevel(LogLevel.VERBOSE)
    config.setSendInBackground(true)

    config.setOnAttributionChangedListener {}

    config.setOnEventTrackingSucceededListener {}

    config.setOnEventTrackingFailedListener { }

    config.setOnSessionTrackingSucceededListener { }

    config.setOnSessionTrackingFailedListener { }

    config.setOnSessionTrackingFailedListener {}

    config.setOnDeeplinkResponseListener { true }
    Adjust.onCreate(config)
    registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
}

private class AdjustLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityResumed(activity: Activity?) {
        Adjust.onResume()
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }

    override fun onActivityPaused(activity: Activity?) {
        Adjust.onPause()
    }
}

fun Activity.trackAdjustEvent(event: String) {
    fireAdjustEvent(event)
}

fun Fragment.trackAdjustEvent(event: String) {
    fireAdjustEvent(event)
}

fun ViewModel.trackAdjustEvent(event: String) {
    fireAdjustEvent(event)
}

fun fireAdjustEvent(event: String) {
    val adjustEvent = AdjustEvent(event)
    Adjust.trackEvent(adjustEvent)
}

