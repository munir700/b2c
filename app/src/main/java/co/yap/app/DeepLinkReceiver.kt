package co.yap.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.airbnb.deeplinkdispatch.DeepLinkHandler


class DeepLinkReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val deepLinkUri = intent!!.getStringExtra(DeepLinkHandler.EXTRA_URI)
        if (intent.getBooleanExtra(DeepLinkHandler.EXTRA_SUCCESSFUL, false)) {
            Log.i("DeepLinkReceiver", "Success deep linking: $deepLinkUri")
        } else {
            val errorMessage =
                intent.getStringExtra(DeepLinkHandler.EXTRA_ERROR_MESSAGE)
            Log.e(
                "DeepLinkReceiver",
                "Error deep linking: $deepLinkUri with error message +$errorMessage"
            )
        }
    }
}