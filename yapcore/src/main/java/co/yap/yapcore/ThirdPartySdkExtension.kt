package co.yap.yapcore

import android.app.Activity
import android.app.Application
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.INVITEE_RECEIEVED_DATE
import co.yap.yapcore.constants.Constants.INVITER_ADJUST_ID
import co.yap.yapcore.constants.Constants.INVITER_ADJUST_URI
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.AdjustEvent
import com.adjust.sdk.LogLevel
import java.net.URL
import java.util.*


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
         getInviterInfoFromDeepLinkUri(deeplink)

        Constants.INVITER_ADJUST_URI= deeplink
        true
    }
//    val uri = Uri.parse("https://grwl.adj.st?adjust_t=q3o2z0e_sv94i35&deep_link=yap_referral&user_id=ABD120000")
//    getInviterInfoFromDeepLinkUri(uri)

    //
    Adjust.onCreate(config)
}

private fun getInviterInfoFromDeepLinkUri(data: Uri) {
    INVITER_ADJUST_URI=data
    val url = URL(
        data?.scheme,
        data?.host,
        data?.path
    )

    val customerId = data.getQueryParameter("user_id");
    val date = DateFormat.format(
        "yyyy-MM-dd hh:mm:ss",
        Date()
    ) as String

//    "https://grwl.adj.st?adjust_t=q3o2z0e_sv94i35&deep_link=yap_referral&user_id=" + userId
    INVITER_ADJUST_ID = customerId.toString()
    INVITEE_RECEIEVED_DATE= date
    Log.i("url", url.toString())
    Log.i("urluserid", customerId.toString())
    Log.i(
        "urlDate",
        date.toString()
    )// this is the current dat & time when user is retriving this url on local app

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

