package co.yap.networking.customers.household

import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.household.responsedtos.SubAccounts
import co.yap.networking.customers.household.responsedtos.HouseHoldGetSubscriptionResponseDTO
import co.yap.networking.customers.household.responsedtos.HouseHoldUserProfile
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface CustomerHHApi: CustomersApi {
    //    SubAccount Card Get All subaccounts for a YAP user:
    suspend fun getSubAccounts(): RetroApiResponse<SubAccounts>

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

}