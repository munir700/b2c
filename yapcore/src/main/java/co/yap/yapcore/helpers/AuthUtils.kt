package co.yap.yapcore.helpers

import android.content.Context
import android.content.Intent

object AuthUtils {
    var isFromVerifyPassCode = false

    fun navigateToHardLogin(context: Context) {
        if (!isFromVerifyPassCode) {
            navigateToSoftLogin(context)
        }

        val sharedPreferenceManager = SharedPreferenceManager(context)
        val isFirstTimeUser: Boolean =
            sharedPreferenceManager.getValueBoolien(
                SharedPreferenceManager.KEY_IS_FIRST_TIME_USER,
                false
            )
        val isFingerprintPermissionShown: Boolean =
            sharedPreferenceManager.getValueBoolien(
                SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                false
            )
        val uuid: String? =
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)

        sharedPreferenceManager.clearSharedPreference()
        sharedPreferenceManager.save(SharedPreferenceManager.KEY_APP_UUID, uuid.toString())
        sharedPreferenceManager.save(
            SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
            isFingerprintPermissionShown
        )
        sharedPreferenceManager.save(
            SharedPreferenceManager.KEY_IS_FIRST_TIME_USER,
            isFirstTimeUser
        )
    }

    fun navigateToHardLoginFromVerifyPassCode(context: Context) {
        isFromVerifyPassCode = true
        navigateToHardLogin(context)
    }

    fun navigateToSoftLogin(context: Context) {
        val loginIntent = Intent("co.yap.app.OPEN_LOGIN")
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        context.startActivity(loginIntent)
    }
}