package co.yap.yapcore.helpers.extentions

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseState
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.leanplum.UserAttributes
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

fun Fragment.trackEventWithAttributes(
    user: AccountInfo?,
    signup_length: String = "",
    account_active: Boolean = false,
    context: Context? = null
) {
    trackEventWithAttributes(user,signup_length,account_active,context)
}

fun ViewModel.trackEventWithAttributes(
    user: AccountInfo?,
    signup_length: String = "",
    account_active: Boolean = false,
    context: Context? = null
) {
    user?.let {
        val info: HashMap<String, Any> = HashMap()
        info[UserAttributes().accountType] = it.accountType ?: ""
        info[UserAttributes().email] = it.currentCustomer.email ?: ""
        info[UserAttributes().nationality] = it.currentCustomer.nationality ?: ""
        info[UserAttributes().firstName] = it.currentCustomer.firstName ?: ""
        info[UserAttributes().lastName] = it.currentCustomer.lastName
        info[UserAttributes().documentsVerified] = it.documentsVerified ?: false
        info[UserAttributes().mainUser] = it.accountType == "B2C_ACCOUNT"
        info[UserAttributes().householdUser] = it.accountType == "B2C_HOUSEHOLD"
        info[UserAttributes().youngUser] = false
        info[UserAttributes().b2bUser] = false
        info[UserAttributes().country] = "United Arab Emirates"
        info[UserAttributes().city] = "UNKNOWN"
        info[UserAttributes().signup_timestamp] = it.creationDate ?: System.currentTimeMillis()
        info[UserAttributes().faceID_enabled] = context?.let {
            return@let SharedPreferenceManager(context).getValueBoolien(
                Constants.KEY_TOUCH_ID_ENABLED,
                false
            )
        } ?: false
        info[UserAttributes().account_active] = account_active
        if (!signup_length.isNullOrEmpty())
            info[UserAttributes().signup_length] = signup_length
        it.currentCustomer.customerId?.let { customerId ->
            info[UserAttributes().customerId] = customerId
        }
        it.uuid?.let { Leanplum.setUserAttributes(it, info) }
    }
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