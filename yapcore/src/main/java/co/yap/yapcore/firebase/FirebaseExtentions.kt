package co.yap.yapcore.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics


@SuppressLint("MissingPermission")
fun Context?.firebaseTagManagerEvent(tagModel: FirebaseTagManagerModel) {
    this?.let {
        FirebaseAnalytics.getInstance(it).let { firebaseAnalytics ->
            tagModel.label?.let { label ->
                val bundle = Bundle()
                bundle.putString("TrackType", "Event")
                bundle.putString("Category", tagModel.category)
                bundle.putString("Action", tagModel.action)
                bundle.putString("Label", "Android")
                firebaseAnalytics.logEvent(tagModel.action ?: FirebaseAnalytics.Param.ITEMS, bundle)
            }
        }
    }
}