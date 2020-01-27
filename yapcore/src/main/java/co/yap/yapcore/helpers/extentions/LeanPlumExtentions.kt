package co.yap.yapcore.helpers.extentions

import android.app.Activity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.leanplum.Leanplum

fun TextView.trackEvent(eventName: String) {
    fireEvent(eventName)
}

fun Button.trackEvent(eventName: String) {
    fireEvent(eventName)
}

fun ImageView.trackEvent(eventName: String) {
    fireEvent(eventName)
}

fun Fragment.trackEvent(eventName: String) {
    fireEvent(eventName)
}

fun Activity.trackEvent(eventName: String) {
    fireEvent(eventName)
}

fun ViewModel.trackEvent(eventName: String) {
    fireEvent(eventName)
}


fun ViewModel.trackEventWithAttributes(attributes: Map<String, *>) {
    Leanplum.setUserAttributes(attributes)
}

fun fireEvent(eventName: String) {
    Leanplum.track("B2C: $eventName")

}