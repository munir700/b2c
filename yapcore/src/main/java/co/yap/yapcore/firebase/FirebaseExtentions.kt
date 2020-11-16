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
//                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, label)
//                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, label)
//                firebaseAnalytics.logEvent(FirebaseAnalytics.Param.ITEM_NAME, bundle)
            }
        }
    }
}

//Old method
//private fun setUpFirebaseAnalytics() {
//    val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
//    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
//        param(FirebaseAnalytics.Param.ITEM_ID, "yapTestID")
//        param(FirebaseAnalytics.Param.ITEM_NAME, "SOME_TEST")
//        param(FirebaseAnalytics.Param.CONTENT_TYPE, "text")
//    }
//}