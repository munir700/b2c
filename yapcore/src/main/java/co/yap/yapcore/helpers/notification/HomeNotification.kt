package co.yap.networking.notification

import co.yap.networking.models.ApiResponse
import co.yap.yapcore.enums.NotificationAction

data class HomeNotification(
    val id: String="",
    val title: String = "",
    val description: String,
    val action: NotificationAction
): ApiResponse()