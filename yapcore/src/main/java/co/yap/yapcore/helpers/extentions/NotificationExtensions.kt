package co.yap.yapcore.helpers.extentions

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import co.yap.yapcore.R

fun NotificationManager.sendNotification(
    notificationTitle: String,
    messageBody: String,
    applicationContext: Context
) {
    val notificationId = 0
    val builder: NotificationCompat.Builder =
        NotificationCompat.Builder(
            applicationContext,
            "NotificationChannelId"
        )
            .setSmallIcon(R.drawable.ic_yap)
            .setContentTitle(notificationTitle)
            .setContentText(messageBody)
    notify(notificationId, builder.build())
}