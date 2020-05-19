package co.yap.networking.notification

import co.yap.networking.models.ApiResponse

data class HomeNotification(
    val id: String="",
    val title: String = "",
    val description: String,
    val action: NotificationAction
): ApiResponse()