package co.yap.yapcore.firebase

import android.util.Log
import co.yap.yapcore.constants.Constants.KEY_FCM_TOKEN
import co.yap.yapcore.helpers.SharedPreferenceManager
import com.google.firebase.messaging.RemoteMessage
import com.leanplum.LeanplumPushFirebaseMessagingService

class YapFirebaseMessagingService : LeanplumPushFirebaseMessagingService() {
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        SharedPreferenceManager.getInstance(applicationContext).save(KEY_FCM_TOKEN, token)
        Log.d("YapFirebaseMessaging>>" , token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
    }

}