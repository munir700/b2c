package co.yap.modules.others.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.adjust.sdk.Adjust
import com.adjust.sdk.Constants

// support multiple BroadcastReceivers for the INSTALL_REFERRER:
// https://appington.wordpress.com/2012/08/01/giving-credit-for-android-app-installs/
class AdjustReferrerReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val rawReferrer = intent.getStringExtra(Constants.REFERRER) ?: return
        Adjust.getDefaultInstance().sendReferrer(rawReferrer, context)
    }
}