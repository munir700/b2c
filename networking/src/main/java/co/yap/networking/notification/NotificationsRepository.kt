package co.yap.networking.notification

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.customers.requestdtos.MsCustomerNotificationsRequest
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.requestdtos.FCMTokenRequest
import co.yap.networking.notification.responsedtos.FcmNotificationCount
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.networking.notification.responsedtos.MsTokenResponse

object NotificationsRepository : BaseRepository(), NotificationsApi {
    const val URL_MS_LOGIN_TOKEN = "analytics/api/notifications/customer-token "
    const val URL_CUSTOMER_NOTIFICATIONS =
        "analytics/api/customer-notifications" //FCM notifications
    const val URL_CUSTOMER_NOTIFICATION_READABLE =
        "analytics/api/customer-notification/is-read" //FCM notifications
    const val URL_CUSTOMER_NOTIFICATION_COUNT =
        "analytics/api/customer-notifications/unread-count" //FCM notifications
    const val URL_DELETE_CUSTOMER_NOTIFICATION =
        "analytics/api/customer_notification" //FCM notifications
    private val api: NotificationsRetroService =
        RetroNetwork.createService(NotificationsRetroService::class.java)

    //FCM API
    override suspend fun sendFcmTokenToServer(msObject: FCMTokenRequest): RetroApiResponse<BaseResponse<MsTokenResponse>> =
        executeSafely(call = { api.sendFcmTokenToServer(msObject) })

    override suspend fun getAllNotifications(): RetroApiResponse<BaseListResponse<HomeNotification>> =
        executeSafely(call = {
            api.getTransactionsNotifications()
        })

    override suspend fun updateReadNotifications(
        notificationId: String,
        isRead: Boolean
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.updateMsNotificationsRead(
                notificationId,
                isRead
            )
        })

    override suspend fun deleteMsCustomerNotification(notificationId: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.deleteMsCustomerNotification(
                notificationId
            )
        })

    override suspend fun getTransactionsNotificationsCount(): RetroApiResponse<BaseResponse<FcmNotificationCount>> =
        executeSafely(call = { api.getTransactionsNotificationsCount() })

}