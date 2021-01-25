package co.yap.networking.notification

import co.yap.networking.customers.responsedtos.CustomerNotificationResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.notification.requestdtos.FCMTokenRequest
import co.yap.networking.notification.responsedtos.FcmNotificationCount
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.networking.notification.responsedtos.MsTokenResponse
import retrofit2.Response
import retrofit2.http.*

interface NotificationsRetroService {
    @POST(NotificationsRepository.URL_MS_LOGIN_TOKEN)
    suspend fun sendFcmTokenToServer(
        @Body msTokenRequest: FCMTokenRequest
    ): Response<BaseResponse<MsTokenResponse>>

    @GET(NotificationsRepository.URL_CUSTOMER_NOTIFICATIONS)
    suspend fun getTransactionsNotifications(
    ): Response<BaseListResponse<HomeNotification>>

    @GET(NotificationsRepository.URL_CUSTOMER_NOTIFICATION_COUNT)
    suspend fun getTransactionsNotificationsCount(): Response<BaseResponse<FcmNotificationCount>>

    @PUT(NotificationsRepository.URL_CUSTOMER_NOTIFICATION_READABLE)
    suspend fun updateMsNotificationsRead(
        @Query("notificationId") notificationId: String,
        @Query("is_read") is_read: Boolean
    ): Response<ApiResponse>

    @DELETE(NotificationsRepository.URL_DELETE_CUSTOMER_NOTIFICATION)
    suspend fun deleteMsCustomerNotification(@Query("notificationId") notificationId: String): Response<ApiResponse>

}