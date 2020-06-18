package co.yap.networking.customers.household

import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.*
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.*

interface CustomersHHRetroService {
    //   SubAccount Card Get All subaccounts for a IBAN user:
    @GET(CustomersHHRepository.URL_GET_SUB_ACCOUNTS)
    suspend fun getSubAccountAccount(): Response<BaseListResponse<SubAccount>>

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

    @POST(CustomersHHRepository.URL_IBAN_HOUSE_HOLD_SCHEDULE_PAYMENT)
    suspend fun createSchedulePayment(
        @Path("UUID") uuid: String?,
        @Body request: SchedulePayment?
    ): Response<ApiResponse>

    @GET(CustomersHHRepository.URL_IBAN_HOUSE_HOLD_GET_SCHEDULE_PAYMENT)
    suspend fun getSchedulePayment(
        @Path("UUID") uuid: String?,
        @Path("category") category: String?
    ): Response<BaseListResponse<SchedulePayment>>

    @GET(CustomersHHRepository.URL_IBAN_HOUSE_HOLD_GET_LAST_TRANSACTION)
    suspend fun getLastTransaction(
        @Path("UUID") uuid: String?,
        @Path("category") category: String?
    ): Response<BaseResponse<SalaryTransaction>>

    @GET(CustomersHHRepository.URL_IBAN_HOUSE_HOLD_GET_LAST_NEXT_TRANSACTION)
    suspend fun getLastNextTransaction(@Path("UUID") uuid: String?): Response<BaseListResponse<HouseHoldLastNextSalary>>

    @POST(CustomersHHRepository.URL_IBAN_HOUSE_HOLD_CANCEL_SCHEDULE_PAYMENT)
    suspend fun cancelSchedulePayment(@Path("UUID") uuid: String?): Response<ApiResponse>

    @POST(CustomersHHRepository.URL_IBAN_HOUSE_HOLD_UPDATE_SCHEDULE_PAYMENT)
    suspend fun updateSchedulePayment(@Path("UUID") uuid: String?,@Body request: SchedulePayment?): Response<ApiResponse>
}