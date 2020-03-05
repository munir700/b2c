package co.yap.modules.others.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.INVITER_ADJUST_ID_TEST
import co.yap.yapcore.helpers.SharedPreferenceManager
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustReferrerReceiver
import java.net.URL


class InstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) { // Adjust receiver.
        AdjustReferrerReceiver().onReceive(context, intent)
        // Google Analytics receiver.

        val data: Uri? = intent?.data
        Adjust.appWillOpenUrl(data, context)

        if (null != data) {
            val url = URL(
                data?.scheme,
                data?.host,
                data?.path
            )
            INVITER_ADJUST_ID_TEST = data.toString()
            context?.let {
                SharedPreferenceManager(it).save(
                    Constants.INVITER_ADJUST_ID_TEST,
                    data.toString() + "local"
                )
            }

            // And any other receiver which needs the intent.
        }
    }
}