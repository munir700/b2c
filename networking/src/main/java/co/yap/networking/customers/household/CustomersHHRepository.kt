package co.yap.networking.customers.household

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.models.BaseListResponse
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.HouseHoldGetSubscriptionResponseDTO
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object CustomersHHRepository : BaseRepository(), CustomersApi by CustomersRepository,
    CustomerHHApi {
    private var apiService: CustomersHHRetroService =
        RetroNetwork.createService(CustomersHHRetroService::class.java)

    /**
     * House Hold Employee interface APIS (Sub Accounts)
     **/
    const val URL_GET_SUB_ACCOUNTS = "/customers/api/account/get-sub-accounts/"
    const val URL_REFUND_REMOVE_HOUSEHOLD =
        "/customers/api/household/refund-remove-household/{UUID}"
    const val URL_RESEND_HOUSEHOLD = "/customers/api/household/resend-household/{UUID}"
    const val URL_GET_PROFILE_HOUSEHOLD_USER = "/customers/api/household/household-user/{UUID}"
    const val URL_GET_HOUSE_HOLD_SUBSCRIPTION = "customers/api/household/get-subscription/{UUID}"
    const val URL_SETUP_HOUSE_HOLD_SUBSCRIPTION =
        "customers/api/household/setup-subscription/{UUID}"
    const val URL_CANCEL_HOUSE_HOLD_SUBSCRIPTION =
        "customers/api/household/cancel-subscription/{UUID}"
    const val URL_REACTIVATE_HOUSE_HOLD_SUBSCRIPTION =
        "customers/api/household/reactivate-subscription/{UUID}"
    const val URL_IBAN_HOUSE_HOLD_SCHEDULE_PAYMENT =
        "customers/api/household/iban-household-schedule-payment/{UUID}"
    const val URL_IBAN_HOUSE_HOLD_UPDATE_SCHEDULE_PAYMENT =
        "customers/api/household/update-household-schedule-payment/{UUID}"
    const val URL_IBAN_HOUSE_HOLD_GET_SCHEDULE_PAYMENT =
        "customers/api/household/get-scheduled-payment/{UUID}/{category}"
    const val URL_IBAN_HOUSE_HOLD_GET_LAST_TRANSACTION =
        "customers/api/household/get-last-transaction/{UUID}/{category}"
    const val URL_IBAN_HOUSE_HOLD_GET_LAST_NEXT_TRANSACTION =
        "customers/api/household/last-next-transaction/{UUID}"

    //    iban-household-schedule-payment
    //    Get All subaccounts for a IBAN user:
    override suspend fun getSubAccounts(): RetroApiResponse<BaseListResponse<SubAccount>> =
        executeSafely(call = { apiService.getSubAccountAccount() })

    override suspend fun getHouseholdUser(uuid: String?) =
        executeSafely(call = { apiService.getHouseholdUser(uuid) })

    // Resend request to  house hold user from IBAN user
    override suspend fun resendRequestToHouseHoldUser(uuid: String?) =
        executeSafely(call = {
            apiService.resendRequestToHouseHoldUser(
                uuid
            )
        })

    // Remove house hold user from IBAN Sub Account
    override suspend fun RemoveRefundHouseHoldUser(uuid: String?) =
        executeSafely(call = {
            apiService.RemoveRefundHouseHoldUser(
                uuid
            )
        })

    //     Get House Hold user subscription From Iban user
    override suspend fun getHouseHoldSubscription(uuid: String?): RetroApiResponse<HouseHoldGetSubscriptionResponseDTO> =
        executeSafely(call = {
            apiService.getHouseHoldSubscription(
                uuid
            )
        })

    override suspend fun setUpHouseHoldSubscription(
        uuid: String?,
        planType: String?, isAutoRenew: Boolean?
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            apiService.setUpHouseHoldSubscription(
                uuid,
                planType,
                isAutoRenew
            )
        })

    override suspend fun cancelHouseHoldSubscription(uuid: String?): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            apiService.cancelHouseHoldSubscription(
                uuid
            )
        })

    override suspend fun reActivateHouseHoldSubscription(uuid: String?): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            apiService.reActivateHouseHoldSubscription(
                uuid
            )
        })

    override suspend fun createSchedulePayment(
        uuid: String?,
        schedulePayment: SchedulePayment?
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = { apiService.createSchedulePayment(uuid, schedulePayment) })

    override suspend fun updateSchedulePayment(
        uuid: String?,
        schedulePayment: SchedulePayment?
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = { apiService.updateSchedulePayment(uuid, schedulePayment) })

    override suspend fun getSchedulePayment(uuid: String?,category:String?) =
        executeSafely(call = { apiService.getSchedulePayment(uuid,category) })

    override suspend fun getLastTransaction(uuid: String?,category:String?) =
        executeSafely(call = { apiService.getLastTransaction(uuid,category) })

    override suspend fun getLastNextTransaction(uuid: String?)=
        executeSafely(call = { apiService.getLastNextTransaction(uuid) })
}
