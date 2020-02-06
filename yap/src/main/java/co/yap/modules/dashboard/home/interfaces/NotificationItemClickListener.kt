package co.yap.modules.dashboard.home.interfaces

import co.yap.modules.yapnotification.models.Notification

interface NotificationItemClickListener {
    fun onClick(notification: Notification)
}