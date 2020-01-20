package co.yap.yapcore.helpers.extentions

import android.app.Activity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.leanplum.Leanplum

fun TextView.trackEvent(eventName: String) {
    Leanplum.track(eventName)
}

fun Button.trackEvent(eventName: String) {
    Leanplum.track(eventName)
}

fun ImageView.trackEvent(eventName: String) {
    Leanplum.track(eventName)
}

fun Fragment.trackEvent(eventName: String) {
    Leanplum.track(eventName)
}

fun Activity.trackEvent(eventName: String) {
    Leanplum.track(eventName)
}
