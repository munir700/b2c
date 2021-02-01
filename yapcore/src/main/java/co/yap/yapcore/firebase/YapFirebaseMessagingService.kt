package co.yap.yapcore.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import co.yap.yapcore.constants.Constants.KEY_FCM_TOKEN
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.sendNotification
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
        Log.d("YapFirebaseMessaging>>", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        createNotificationChannel()
        val notificationManager =
            getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(
            notificationTitle = "Yap notification",
            messageBody = "Here is the yap testing notification", applicationContext = applicationContext
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val morningNotificationChannel =
                NotificationChannel(
                    "NotificationChannelId",
                    "NotificationChannelName",
                    NotificationManager.IMPORTANCE_HIGH
                )
            morningNotificationChannel.enableLights(true)
            morningNotificationChannel.lightColor = Color.BLUE
            morningNotificationChannel.enableVibration(true)

            val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(morningNotificationChannel)
        }
    }

}