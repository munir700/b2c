package co.yap.yapcore.helpers

import android.content.Context
import android.content.Intent
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.constants.Constants.KEY_IS_FINGERPRINT_PERMISSION_SHOWN
import co.yap.yapcore.constants.Constants.KEY_IS_FIRST_TIME_USER
import co.yap.yapcore.referral.ReferralInfo
import java.util.*

object AuthUtils {

    fun navigateToHardLogin(context: Context, isOnPassCode: Boolean = false) {

        if (!isOnPassCode)
            navigateToSoftLogin(context)
        val sharedPreferenceManager = SharedPreferenceManager(context)
        val isFirstTimeUser: Boolean =
            sharedPreferenceManager.getValueBoolien(
                KEY_IS_FIRST_TIME_USER,
                false
            )
        val isFingerprintPermissionShown: Boolean =
            sharedPreferenceManager.getValueBoolien(
                KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                false
            )
        //Removing it will take user to otp screen will login after logout
        //val uuid: String? =
        //sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        val referralInfo:ReferralInfo?= sharedPreferenceManager.getReferralInfo()
        sharedPreferenceManager.clearSharedPreference()
        sharedPreferenceManager.setReferralInfo(referralInfo)

        //sharedPreferenceManager.save(SharedPreferenceManager.KEY_APP_UUID, uuid.toString())
        sharedPreferenceManager.save(
            KEY_APP_UUID,
            UUID.randomUUID().toString()
        )
        sharedPreferenceManager.save(
            KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
            isFingerprintPermissionShown
        )
        sharedPreferenceManager.save(
            KEY_IS_FIRST_TIME_USER,
            isFirstTimeUser
        )
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