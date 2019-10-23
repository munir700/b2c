package co.yap.modules.dashboard.home.interfaces

import co.yap.modules.dashboard.home.models.Notification

interface NotificationItemClickListener {
    fun onClick(notification: Notification)
}