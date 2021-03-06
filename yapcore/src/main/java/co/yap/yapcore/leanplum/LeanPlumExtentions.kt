package co.yap.yapcore.leanplum

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseState
import co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.biometric.BiometricUtil
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.leanplum.Leanplum
import com.leanplum.callbacks.VariablesChangedCallback
import java.text.SimpleDateFormat

fun trackEvent(eventName: String, value: String = "") {
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

fun Fragment.trackEventInFragments(
        user: AccountInfo?,
        signup_length: String? = null,
        account_active: String? = null,
        context: Context? = null,
        eidExpire: Boolean = false,
        eidExpireDate: String = "",
        city: String? = null,
        isMainUser: Boolean = false,
        isAccountActive: Boolean = false,
        accountActiveMonthly: Boolean = false,
        emailVerified: Boolean = false,
        phoneNumberVerified: Boolean = false,
        account_cancel_timestamp: String? = null,
        expense_pots: String? = null,
        card_color: String? = null
) {
    trackAttributes(
            user,
            signup_length,
            account_active,
            context,
            eidExpire,
            eidExpireDate, city,
            isMainUser,
            isAccountActive,
            accountActiveMonthly,
            emailVerified,
            phoneNumberVerified,
            account_cancel_timestamp,
            expense_pots,
            card_color
    )
}

fun ViewModel.trackEventWithAttributes(
        user: AccountInfo?,
        signup_length: String? = null,
        account_active: String? = null,
        context: Context? = null,
        eidExpire: Boolean = false,
        eidExpireDate: String = "",
        city: String? = null,
        isMainUser: Boolean = false,
        isAccountActive: Boolean = false,
        accountActiveMonthly: Boolean = false,
        emailVerified: Boolean = false,
        phoneNumberVerified: Boolean = false,
        account_cancel_timestamp: String? = null,
        expense_pots: String? = null,
        card_color: String? = null
) {
    trackAttributes(
            user,
            signup_length,
            account_active,
            context,
            eidExpire,
            eidExpireDate, city,
            isMainUser,
            isAccountActive,
            accountActiveMonthly,
            emailVerified,
            phoneNumberVerified,
            account_cancel_timestamp,
            expense_pots,
            card_color
    )
}

fun ViewModel.trackKfsWithAttributes(
    smsNotifications: Boolean? = false,
    emailNotifications: Boolean? = false,
    inAppNotifications: Boolean? = false
) {
    trackKFSAttributes(
        smsNotifications,
        emailNotifications,
        inAppNotifications
    )
}

fun trackEventWithAttributes(
    user: AccountInfo?,
    signup_length: String? = null,
    account_active: String? = null,
    context: Context? = null,
    eidExpire: Boolean = false,
    eidExpireDate: String = "",
    city: String? = null
) {
    trackAttributes(
        user,
        signup_length,
        account_active,
        context,
        eidExpire,
        eidExpireDate, city
    )
}

private fun trackAttributes(
    user: AccountInfo?,
    signup_length: String? = null,
    account_active: String? = null,
    context: Context? = null,
    eidExpire: Boolean = false,
    eidExpireDate: String = "",
    city: String?,
    isMainUser: Boolean = false,
    isAccountActive: Boolean = false,
    accountActiveMonthly: Boolean = false,
    emailVerified: Boolean = false,
    phoneNumberVerified: Boolean = false,
    account_cancel_timestamp: String? = null,
    expense_pots: String? = null,
    card_color: String? = null,
    smsNotifications: Boolean? = false,
    emailNotifications: Boolean? = false,
    inappNotifications: Boolean? = false
) {
    user?.let { it ->
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
        info[UserAttributes().emailVerified] = it.currentCustomer.isEmailVerified ?: false
        info[UserAttributes().phoneNumberVerified] = it.currentCustomer.isMobileNoVerified ?: false
        info[UserAttributes().city] = city ?: "UNKNOWN"
        info[UserAttributes().signup_timestamp] = getFormattedDate(it.creationDate)
        info[UserAttributes().biometric_login_enabled] =
                isBioMetricEnabled(context)
        info[UserAttributes().account_active] =
                account_active ?: (PartnerBankStatus.ACTIVATED.status == it.partnerBankStatus)
        info[UserAttributes().eid_expired] = eidExpire
        info[UserAttributes().eid_expiry_date] = eidExpireDate
        signup_length?.let {
            info[UserAttributes().signup_length] = signup_length
        }
        it.currentCustomer.customerId?.let { customerId ->
            info[UserAttributes().customerId] = customerId
            Firebase.analytics.setUserId(customerId)
        }
        info[UserAttributes().sMS_MarketingConsent] = smsNotifications ?: false
        info[UserAttributes().email_MarketingConsent] = emailNotifications ?: false
        info[UserAttributes().in_AppMessage_MarketingConsent] = inappNotifications ?: false
        info[UserAttributes().isMainUser] = isMainUser
        info[UserAttributes().isAccountActive] = isAccountActive
        info[UserAttributes().accountActiveMonthly] = accountActiveMonthly
        info[UserAttributes().emailVerified] = emailVerified
        info[UserAttributes().phoneNumberVerified] = phoneNumberVerified
        account_cancel_timestamp?.let { info[UserAttributes().account_cancel_timestamp] = it }
        expense_pots?.let { info[UserAttributes().expense_pots] = it }
        card_color?.let { info[UserAttributes().expense_pots] = it }

        it.uuid?.let {  uuid ->
            Leanplum.setUserAttributes(uuid, info)
            Leanplum.setUserId(uuid)
        }
        Leanplum.forceContentUpdate(object : VariablesChangedCallback() {
            override fun variablesChanged() {

            }
        })
    }
}

private fun trackKFSAttributes(
    smsNotifications: Boolean? = false,
    emailNotifications: Boolean? = false,
    inappNotifications: Boolean? = false
) {
    val info: HashMap<String, Any> = HashMap()
    info[UserAttributes().sMS_MarketingConsent] = smsNotifications ?: false
    info[UserAttributes().email_MarketingConsent] = emailNotifications ?: false
    info[UserAttributes().in_AppMessage_MarketingConsent] = inappNotifications ?: false
    Leanplum.forceContentUpdate(object : VariablesChangedCallback() {
        override fun variablesChanged() {

        }
    })
}

@SuppressLint("SimpleDateFormat")
fun getFormattedDate(creationDate: String?): String {
    creationDate?.let { createDate ->
        return try {
            SimpleDateFormat(DateUtils.LEAN_PLUM_EVENT_FORMAT).format(
                    DateUtils.stringToDate(
                            createDate,
                            DateUtils.SERVER_DATE_FORMAT
                    )?.time
            )
        } catch (e: Exception) {
            SimpleDateFormat(DateUtils.LEAN_PLUM_EVENT_FORMAT).format(
                    System.currentTimeMillis()
            )
        }
    } ?: return SimpleDateFormat(DateUtils.LEAN_PLUM_EVENT_FORMAT).format(
            System.currentTimeMillis()
    )
}

private fun isBioMetricEnabled(context: Context?): Boolean {
    return context?.let {
        return@let (BiometricUtil.hasBioMetricFeature(it) && SharedPreferenceManager.getInstance(it)
            .getValueBoolien(
                KEY_TOUCH_ID_ENABLED,
                false
            ))

    } ?: false
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

fun ViewModel.trackEventWithAttributes(
        uuid: String?,
        info: HashMap<String, Any?>
) {
    Leanplum.setUserAttributes(uuid, info)
}

fun Fragment.trackEvent(
        eventName: String,
        lastCountry: String? = null,
        lastType: String? = null
) {

    val params: HashMap<String, Any> = HashMap()
    params["LastCountry"] = lastCountry ?: ""
    params["LastType"] = lastType ?: ""

    fireEvent(eventName, params)
}

fun fireEvent(eventName: String, params: Map<String, Any>) {
    Leanplum.track(eventName, params)
}

fun deleteLeanPlumMessage(messageId: String?) {
    try {
        messageId?.let {
            val message = Leanplum.getInbox().messageForId(it)
            message.remove()
        }
    } catch (e: Exception) {
    }
}

fun markReadLeanPlumMessage(messageId: String?) {
    try {
        messageId?.let {
            val message = Leanplum.getInbox().messageForId(it)
            message.read()
        }

    } catch (e: Exception) {
    }
}