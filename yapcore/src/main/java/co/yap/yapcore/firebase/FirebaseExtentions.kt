package co.yap.yapcore.firebase

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

fun Context?.firebaseTagManagerEvent(tagModel: FirebaseTagManagerModel) {
    this?.let {
        FirebaseAnalytics.getInstance(it).logEvent(tagModel.action, null)
    }
}

/**
 * Log Events manual on  firebase.
 * <p>
 * @param event the event that will be logged see {@link FirebaseEvent}
 * See also [FirebaseEvent].
 */
fun trackEventWithScreenName(event: FirebaseEvent) {
    val firebaseAnalytics = Firebase.analytics
    event.event?.let { e ->
        firebaseAnalytics.logEvent(e.trim()) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, event.screenName?.trim() ?: "")

        }
    }
}

/**
 * Log Events manual on  firebase.
 * <p>
 * @param event the event that will be logged see {@link FirebaseEvent}
 *@param additionalParams The additional Params that will be logged with event
 * See also [FirebaseEvent].
 */

fun trackEventWithScreenName(event: FirebaseEvent, additionalParams: Bundle? = null) {
    val firebaseAnalytics = Firebase.analytics
    event.event?.let { e ->
        val Params =
            bundleOf(FirebaseAnalytics.Param.SCREEN_NAME to (event.screenName?.trim() ?: ""))
        additionalParams?.let {
            if (it.keySet().size > 0)
                Params.putAll(it)
        }
        firebaseAnalytics.logEvent(e.trim(), Params)
    }
}

fun setUserId() {

}