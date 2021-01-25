package co.yap.networking.notification

import co.yap.networking.customers.requestdtos.MsCustomerNotificationsRequest
import co.yap.networking.customers.responsedtos.CustomerNotificationResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.requestdtos.FCMTokenRequest
import co.yap.networking.notification.responsedtos.FcmNotificationCount
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.networking.notification.responsedtos.MsTokenResponse

interface NotificationsApi {
    suspend fun sendFcmTokenToServer(msObject: FCMTokenRequest): RetroApiResponse<BaseResponse<MsTokenResponse>>
    suspend fun getAllNotifications(): RetroApiResponse<BaseListResponse<HomeNotification>>
    suspend fun updateReadNotifications(notificationId: String, isRead : Boolean): RetroApiResponse<ApiResponse>
    suspend fun getTransactionsNotificationsCount(): RetroApiResponse<BaseResponse<FcmNotificationCount>>
    suspend fun deleteMsCustomerNotification(notificationId: String): RetroApiResponse<ApiResponse>
}