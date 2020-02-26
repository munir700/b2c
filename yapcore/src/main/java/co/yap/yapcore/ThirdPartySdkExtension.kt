package co.yap.yapcore

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.adjust.sdk.*


/*
* Following sdk's included
* -> Adjust SDK
* */

fun Application.initializeAdjustSdk(appToken: String) {
    val environment: String
    environment = AdjustConfig.ENVIRONMENT_SANDBOX
    val config = AdjustConfig(this, appToken, environment)
    config.setLogLevel(LogLevel.VERBOSE);
    Adjust.onCreate(config)

//    if (!BuildConfig.DEBUG) {// will modify this check for production later
//        environment = AdjustConfig.ENVIRONMENT_PRODUCTION
//        val config = AdjustConfig(this, appToken, environment, true)
//        config.setLogLevel(LogLevel.SUPRESS);
//        Adjust.onCreate(config)
//    } else {
//        environment = AdjustConfig.ENVIRONMENT_SANDBOX
//        val config = AdjustConfig(this, appToken, environment)
//        config.setLogLevel(LogLevel.VERBOSE);
//        Adjust.onCreate(config)
//    }
    registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())

    // add Session and event callbacks
// Set event success tracking delegate.
    // Set event success tracking delegate.
    config.setOnEventTrackingSucceededListener {
        // ...
    }

// Set event failure tracking delegate.
    // Set event failure tracking delegate.
    config.setOnEventTrackingFailedListener {
        // ...
    }

// Set session success tracking delegate.
    // Set session success tracking delegate.
    config.setOnSessionTrackingSucceededListener {
        // ...
    }

// Set session failure tracking delegate.
    // Set session failure tracking delegate.
    config.setOnSessionTrackingFailedListener {
        // ...
    }
//


    // Evaluate deferred deep link to be launched.
    config.setOnDeeplinkResponseListener { deeplink ->
        Log.v("example", "Deferred deep link callback called!")
        Log.v("example", "Deep link URL: $deeplink")

        true
    }

     //
    Adjust.onCreate(config)
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

    //




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

//

