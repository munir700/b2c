package co.yap.yapcore.helpers

import android.content.Context
import android.content.Intent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.INVITEE_RECEIEVED_DATE
import co.yap.yapcore.constants.Constants.INVITER_ADJUST_ID
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.constants.Constants.KEY_IS_FINGERPRINT_PERMISSION_SHOWN
import co.yap.yapcore.constants.Constants.KEY_IS_FIRST_TIME_USER
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
        //val uuid: String? =
        //    sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)

        var inviterLinkDate:String?= null
        var inviterId:String?= null
        if (!SharedPreferenceManager(context).getValueString(
                Constants.INVITEE_RECEIEVED_DATE
            ).isNullOrEmpty() && !SharedPreferenceManager(context).getValueString(
                Constants.INVITER_ADJUST_ID
            ).isNullOrEmpty()
        ){

            inviterLinkDate = SharedPreferenceManager(context).getValueString(
                Constants.INVITEE_RECEIEVED_DATE
            )
            inviterId = SharedPreferenceManager(context).getValueString(
                Constants.INVITER_ADJUST_ID
            )
        }
        sharedPreferenceManager.clearSharedPreference()
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
        inviterId?.let {
            sharedPreferenceManager.save(
                INVITER_ADJUST_ID,
                it
            )
        }
        
        inviterLinkDate?.let {
            sharedPreferenceManager.save(
                INVITEE_RECEIEVED_DATE,
                it
            )
        }
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