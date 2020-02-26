package co.yap.yapcore.helpers.extentions

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import co.yap.yapcore.BaseState
import com.leanplum.Leanplum


fun Fragment.trackEvent(eventName: String, value: String = "") {
    fireEventWithAttribute(eventName, value)
}

fun Activity.trackEvent(eventName: String, value: String = "") {
    fireEventWithAttribute(eventName, value)
}

fun ViewModel.trackEvent(eventName: String, value: String = "") {
    fireEventWithAttribute(eventName, value)
}

fun BaseState.trackEvent(eventName: String, value: String = "") {
    fireEventWithAttribute(eventName, value)
}

fun ViewModel.trackEventWithAttributes(attributes: Map<String, *>) {
    Leanplum.setUserAttributes(attributes)
}

fun ViewModel.trackEventWithAttributes(clientId: String, attributes: Map<String, *>) {
    Leanplum.setUserAttributes(clientId, attributes)
}

fun ViewModel.trackerId(id: String?) {
    Leanplum.setUserId(id)
}

fun fireEventWithAttribute(eventName: String, value: String) {
    if (value.isBlank()) {
        Leanplum.track(eventName)
    } else {
        Leanplum.track(eventName, value)
    }
}