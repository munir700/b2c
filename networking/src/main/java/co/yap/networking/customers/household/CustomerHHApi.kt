package co.yap.networking.customers.household

import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.*
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import retrofit2.http.Body

interface CustomerHHApi : CustomersApi {
    //    SubAccount Card Get All subaccounts for a YAP user:
    suspend fun getSubAccounts(): RetroApiResponse<BaseListResponse<SubAccount>>

    // SubAccount Card Get HouseHold User Info
    suspend fun getHouseholdUser(uuid: String?): RetroApiResponse<HouseHoldUserProfile>

    //   SubAccount Card Resend Household onboarding
    suspend fun resendRequestToHouseHoldUser(uuid: String?): RetroApiResponse<ApiResponse>

    //    SubAccount Card Remove house hold card and Refund to IBAN user
    suspend fun RemoveRefundHouseHoldUser(uuid: String?): RetroApiResponse<ApiResponse>

    suspend fun getHouseHoldSubscription(uuid: String?): RetroApiResponse<HouseHoldGetSubscriptionResponseDTO>
    suspend fun setUpHouseHoldSubscription(
        uuid: String?,
        planType: String?, isAutoRenew: Boolean?
    ): RetroApiResponse<ApiResponse>

    suspend fun reActivateHouseHoldSubscription(uuid: String?): RetroApiResponse<ApiResponse>
    suspend fun cancelHouseHoldSubscription(uuid: String?): RetroApiResponse<ApiResponse>
    suspend fun createSchedulePayment(
        uuid: String?,
        schedulePayment: SchedulePayment?
    ): RetroApiResponse<ApiResponse>

    /**
     * @param uuid the sub account user UUID.
     * @param category would be Salary/Expense
     * @return response of last Schedule Salary or Expense in Collection
     */
    suspend fun getSchedulePayment(
        uuid: String?,
        category: String?
    ): RetroApiResponse<BaseListResponse<SchedulePayment>>

    /**
     * @param uuid the sub account user UUID.
     * @param category would be Salary/Expense
     * @return response of last Schedule Salary or Expense in Collection
     */
    suspend fun getLastTransaction(
        uuid: String?,
        category: String?
    ): RetroApiResponse<BaseResponse<SalaryTransaction>>

    suspend fun getLastNextTransaction(uuid: String?): RetroApiResponse<BaseListResponse<HouseHoldLastNextSalary>>
    suspend fun cancelSchedulePayment(scheduledPaymentUuid: String?): RetroApiResponse<ApiResponse>
    suspend fun updateSchedulePayment(scheduledPaymentUuid: String?, request: SchedulePayment?): RetroApiResponse<ApiResponse>
}