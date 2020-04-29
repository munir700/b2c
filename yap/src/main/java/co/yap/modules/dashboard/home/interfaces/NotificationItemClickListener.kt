package co.yap.modules.dashboard.home.interfaces

import co.yap.modules.dashboard.home.models.HomeNotification

interface NotificationItemClickListener {
    fun onClick(notification: HomeNotification)
    fun onCloseClick(notification: HomeNotification) {}
}