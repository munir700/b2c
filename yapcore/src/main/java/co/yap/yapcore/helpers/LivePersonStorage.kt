package co.yap.yapcore.helpers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.annotation.Keep


class LivePersonStorage private constructor(context: Context) {

    var campaignId: Long? = null
    var engagementId: Long? = null
    var sessionId: String? = null
    var visitorId: String? = null
    var interactionContextId: String? = null

    private val mDefaultSharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    var lastName: String?
        get() = mDefaultSharedPreferences.getString(LAST_NAME, "")
        set(lastName) = mDefaultSharedPreferences.edit().putString(LAST_NAME, lastName).apply()

    var firstName: String?
        get() = mDefaultSharedPreferences.getString(FIRST_NAME, "")
        set(firstName) = mDefaultSharedPreferences.edit().putString(
            FIRST_NAME,
            firstName
        ).apply()

    var phoneNumber: String?
        get() = mDefaultSharedPreferences.getString(PHONE_NUMBER, "")
        set(phoneNumber) = mDefaultSharedPreferences.edit().putString(
            PHONE_NUMBER,
            phoneNumber
        ).apply()

    var authCode: String?
        get() = mDefaultSharedPreferences.getString(AUTH_CODE, "")
        set(authCode) = mDefaultSharedPreferences.edit().putString(AUTH_CODE, authCode).apply()

    var publicKey: String?
        get() = mDefaultSharedPreferences.getString(PUBLIC_KEY, "")
        set(publicKey) = mDefaultSharedPreferences.edit().putString(
            PUBLIC_KEY,
            publicKey
        ).apply()

    var sdkMode: SDKMode
        get() {
            val sdkModeInt =
                mDefaultSharedPreferences.getInt(SDK_MODE, SDKMode.ACTIVITY.ordinal)
            return if (sdkModeInt == SDKMode.ACTIVITY.ordinal) SDKMode.ACTIVITY else SDKMode.FRAGMENT
        }
        set(state) = mDefaultSharedPreferences.edit().putInt(SDK_MODE, state.ordinal).apply()

    var account: String?
        get() = mDefaultSharedPreferences.getString(BRAND_ID, "")
        set(account) = mDefaultSharedPreferences.edit().putString(BRAND_ID, account).apply()

    // Monitoring
    var appInstallId: String?
        get() = mDefaultSharedPreferences.getString(APP_INSTALL_ID, "")
        set(appInstallId) = mDefaultSharedPreferences.edit().putString(
            APP_INSTALL_ID,
            appInstallId
        ).apply()

    var consumerId: String?
        get() = mDefaultSharedPreferences.getString(CONSUMER_ID, "")
        set(consumerId) = mDefaultSharedPreferences.edit().putString(
            CONSUMER_ID,
            consumerId
        ).apply()

    var pageId: String?
        get() = mDefaultSharedPreferences.getString(PAGE_ID, "")
        set(pageId) = mDefaultSharedPreferences.edit().putString(PAGE_ID, pageId).apply()
    @Keep
    enum class SDKMode {
        ACTIVITY, FRAGMENT
    }


    companion object {

        private val TAG = LivePersonStorage::class.java.simpleName

        val SDK_SAMPLE_APP_ID = "com.liveperson.sdksample"
        val SDK_SAMPLE_FCM_APP_ID = "com.liveperson.sdksampleFcm"

        // Messaging
        private val FIRST_NAME = "first_name"
        private val LAST_NAME = "last_name"
        private val PHONE_NUMBER = "phone_number"
        private val AUTH_CODE = "auth_code"
        private val SDK_MODE = "sdk_mode"
        private val BRAND_ID = "brand_id"
        private val PUBLIC_KEY = "public_key"

        // Monitoring
        private val APP_INSTALL_ID = "app_install_id"
        private val CONSUMER_ID = "consume_id"
        private val PAGE_ID = "page_id"
        @Volatile
        private var instance: LivePersonStorage? = null

        fun getInstance(context: Context): LivePersonStorage? {
            if (instance == null) {
                synchronized(LivePersonStorage::class.java) {
                    if (instance == null) {
                        instance = LivePersonStorage(context)
                    }
                }
            }
            return instance
        }
    }
}
