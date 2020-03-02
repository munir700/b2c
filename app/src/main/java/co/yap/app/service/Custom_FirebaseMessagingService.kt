package co.yap.app.service

import com.google.firebase.messaging.RemoteMessage
import com.leanplum.LeanplumPushFirebaseMessagingService
import timber.log.Timber

class CustomFirebaseMessagingService : LeanplumPushFirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Timber.d("### Firebase message received")
    }
}