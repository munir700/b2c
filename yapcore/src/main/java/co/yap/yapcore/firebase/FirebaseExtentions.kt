package co.yap.yapcore.firebase

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent


fun Context?.firebaseTagManagerEvent(tagModel: FirebaseTagManagerModel) {
    this?.let {
        FirebaseAnalytics.getInstance(it).let { firebaseAnalytics ->
            tagModel.label?.let { label ->
                firebaseAnalytics.logEvent(FirebaseAnalytics.Param.ITEM_NAME) {
                    param(FirebaseAnalytics.Param.ITEM_ID, label)
                    param(FirebaseAnalytics.Param.ITEM_NAME, label)
                }
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