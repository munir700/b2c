package co.yap.app.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import co.yap.app.activities.MainActivity
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.SharedPreferenceManager

class AuthBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            AuthUtils.navigateToLogin(it)
        }

    }
}