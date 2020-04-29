package co.yap.networking.customers

import co.yap.networking.customers.responsedtos.SubAccounts
import co.yap.networking.customers.responsedtos.household.HouseHoldGetSubscriptionResponseDTO
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object CustomersHHRepository : CustomersApi by CustomersRepository, CustomerHHApi {
    //    Get All subaccounts for a IBAN user:
    override suspend fun getSubAccounts(): RetroApiResponse<SubAccounts> =
        CustomersRepository.executeSafely(call = { CustomersRepository.api.getSubAccountAccount() })

    override suspend fun getHouseholdUser(uuid: String?) =
        CustomersRepository.executeSafely(call = { CustomersRepository.api.getHouseholdUser(uuid) })

    // Resend request to  house hold user from IBAN user
    override suspend fun resendRequestToHouseHoldUser(uuid: String?) =
        CustomersRepository.executeSafely(call = {
            CustomersRepository.api.resendRequestToHouseHoldUser(
                uuid
            )
        })

    // Remove house hold user from IBAN Sub Account
    override suspend fun RemoveRefundHouseHoldUser(uuid: String?) =
        CustomersRepository.executeSafely(call = {
            CustomersRepository.api.RemoveRefundHouseHoldUser(
                uuid
            )
        })

    //     Get House Hold user subscription From Iban user
    override suspend fun getHouseHoldSubscription(uuid: String?): RetroApiResponse<HouseHoldGetSubscriptionResponseDTO> =
        CustomersRepository.executeSafely(call = {
            CustomersRepository.api.getHouseHoldSubscription(
                uuid
            )
        })

    override suspend fun setUpHouseHoldSubscription(
        uuid: String?,
        planType: String?, isAutoRenew: Boolean?
    ): RetroApiResponse<ApiResponse> =
        CustomersRepository.executeSafely(call = {
            CustomersRepository.api.setUpHouseHoldSubscription(
                uuid,
                planType,
                isAutoRenew
            )
        })

    override suspend fun cancelHouseHoldSubscription(uuid: String?): RetroApiResponse<ApiResponse> =
        CustomersRepository.executeSafely(call = {
            CustomersRepository.api.cancelHouseHoldSubscription(
                uuid
            )
        })


    override suspend fun reActivateHouseHoldSubscription(uuid: String?): RetroApiResponse<ApiResponse> =
        CustomersRepository.executeSafely(call = {
            CustomersRepository.api.reActivateHouseHoldSubscription(
                uuid
            )
        })
}