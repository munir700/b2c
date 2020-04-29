package co.yap.networking.customers.household

import co.yap.networking.customers.CustomersRetroService
import co.yap.networking.customers.household.responsedtos.SubAccounts
import co.yap.networking.customers.household.responsedtos.HouseHoldGetSubscriptionResponseDTO
import co.yap.networking.customers.household.responsedtos.HouseHoldUserProfile
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CustomersHHRetroService : CustomersRetroService {
    //   SubAccount Card Get All subaccounts for a IBAN user:
    @GET(CustomersHHRepository.URL_GET_SUB_ACCOUNTS)
    suspend fun getSubAccountAccount(): Response<SubAccounts>

    // SubAccount Card Get HouseHold User Info
    @GET(CustomersHHRepository.URL_GET_PROFILE_HOUSEHOLD_USER)
    suspend fun getHouseholdUser(@Path("UUID") uuid: String?): Response<HouseHoldUserProfile>

    //   SubAccount Card Resend Household onboarding
    @POST(CustomersHHRepository.URL_RESEND_HOUSEHOLD)
    suspend fun resendRequestToHouseHoldUser(@Path("UUID") uuid: String?): Response<ApiResponse>

    //    SubAccount Card Remove house hold card and Refund to IBAN user
    @POST(CustomersHHRepository.URL_REFUND_REMOVE_HOUSEHOLD)
    suspend fun RemoveRefundHouseHoldUser(@Path("UUID") uuid: String?): Response<ApiResponse>

    //     Get House Hold user subscription From Iban user
    @GET(CustomersHHRepository.URL_GET_HOUSE_HOLD_SUBSCRIPTION)
    suspend fun getHouseHoldSubscription(@Path("UUID") uuid: String?): Response<HouseHoldGetSubscriptionResponseDTO>

    @POST(CustomersHHRepository.URL_SETUP_HOUSE_HOLD_SUBSCRIPTION)
    suspend fun setUpHouseHoldSubscription(
        @Path("UUID") uuid: String?,
        @Query("planType") planType: String?, @Query("isAutoRenew") isAutoRenew: Boolean?
    ): Response<ApiResponse>

    @POST(CustomersHHRepository.URL_CANCEL_HOUSE_HOLD_SUBSCRIPTION)
    suspend fun cancelHouseHoldSubscription(@Path("UUID") uuid: String?): Response<ApiResponse>


    @POST(CustomersHHRepository.URL_REACTIVATE_HOUSE_HOLD_SUBSCRIPTION)
    suspend fun reActivateHouseHoldSubscription(@Path("UUID") uuid: String?): Response<ApiResponse>
}