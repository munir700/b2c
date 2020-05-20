package co.yap.modules.dashboard.home.interfaces

import co.yap.networking.notification.HomeNotification

interface NotificationItemClickListener {
    fun onClick(notification: HomeNotification)
    fun onCloseClick(notification: HomeNotification) {}
}