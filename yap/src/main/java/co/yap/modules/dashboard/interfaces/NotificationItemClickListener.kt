package co.yap.modules.dashboard.interfaces

import co.yap.modules.dashboard.models.Notification

interface NotificationItemClickListener {
    fun onClick(notification: Notification)
}