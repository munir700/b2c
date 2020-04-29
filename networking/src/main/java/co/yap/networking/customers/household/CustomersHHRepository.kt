package co.yap.networking.customers.household

import co.yap.networking.RetroNetwork
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.household.responsedtos.SubAccounts
import co.yap.networking.customers.household.responsedtos.HouseHoldGetSubscriptionResponseDTO
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object CustomersHHRepository : CustomersApi by CustomersRepository, CustomerHHApi {

    var api: CustomersHHRetroService =
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

    //    iban-household-schedule-payment
    //    Get All subaccounts for a IBAN user:
    override suspend fun getSubAccounts(): RetroApiResponse<SubAccounts> =
        CustomersRepository.executeSafely(call = { api.getSubAccountAccount() })

    override suspend fun getHouseholdUser(uuid: String?) =
        CustomersRepository.executeSafely(call = { api.getHouseholdUser(uuid) })

    // Resend request to  house hold user from IBAN user
    override suspend fun resendRequestToHouseHoldUser(uuid: String?) =
        CustomersRepository.executeSafely(call = {
            api.resendRequestToHouseHoldUser(
                uuid
            )
        })

    // Remove house hold user from IBAN Sub Account
    override suspend fun RemoveRefundHouseHoldUser(uuid: String?) =
        CustomersRepository.executeSafely(call = {
            api.RemoveRefundHouseHoldUser(
                uuid
            )
        })

    //     Get House Hold user subscription From Iban user
    override suspend fun getHouseHoldSubscription(uuid: String?): RetroApiResponse<HouseHoldGetSubscriptionResponseDTO> =
        CustomersRepository.executeSafely(call = {
            api.getHouseHoldSubscription(
                uuid
            )
        })

    override suspend fun setUpHouseHoldSubscription(
        uuid: String?,
        planType: String?, isAutoRenew: Boolean?
    ): RetroApiResponse<ApiResponse> =
        CustomersRepository.executeSafely(call = {
            api.setUpHouseHoldSubscription(
                uuid,
                planType,
                isAutoRenew
            )
        })

    override suspend fun cancelHouseHoldSubscription(uuid: String?): RetroApiResponse<ApiResponse> =
        CustomersRepository.executeSafely(call = {
            api.cancelHouseHoldSubscription(
                uuid
            )
        })


    override suspend fun reActivateHouseHoldSubscription(uuid: String?): RetroApiResponse<ApiResponse> =
        CustomersRepository.executeSafely(call = {
            api.reActivateHouseHoldSubscription(
                uuid
            )
        })
}