package co.yap.yapcore.firebase

import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics


@SuppressLint("MissingPermission")
fun Context?.firebaseTagManagerEvent(tagModel: FirebaseTagManagerModel) {
    this?.let {
        FirebaseAnalytics.getInstance(it).let { firebaseAnalytics ->
            tagModel.label?.let { label ->
                firebaseAnalytics.logEvent(tagModel.action ?: FirebaseAnalytics.Param.ITEMS, null)
            }
        }
    }
}