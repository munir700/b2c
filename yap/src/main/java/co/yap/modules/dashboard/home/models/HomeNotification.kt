package co.yap.modules.dashboard.home.models

import co.yap.networking.models.ApiResponse
import co.yap.networking.notification.NotificationAction

data class HomeNotification(
    val id: String="",
    val title: String = "",
    val description: String,
    val action: NotificationAction
): ApiResponse()