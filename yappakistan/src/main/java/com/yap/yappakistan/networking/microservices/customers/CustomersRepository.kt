package com.yap.yappakistan.networking.microservices.customers

import com.yap.yappakistan.networking.apiclient.base.ApiResponse
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse
import com.yap.yappakistan.networking.apiclient.base.BaseRepository
import com.yap.yappakistan.networking.apiclient.base.CookiesManager
import com.yap.yappakistan.networking.apiclient.models.BaseListResponse
import com.yap.yappakistan.networking.apiclient.models.BaseResponse
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.DemographicDataRequest
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.*
import com.yap.yappakistan.networking.microservices.customers.responsedtos.ReferralAmount
import com.yap.yappakistan.networking.microservices.customers.responsedtos.WaitingListResponse
import com.yap.yappakistan.networking.microservices.customers.responsedtos.accountinfo.AccountInfo
import javax.inject.Inject

class CustomersRepository @Inject constructor(
    private val service: CustomersRetroService
) : BaseRepository(), CustomersApi {

    override suspend fun emailVerification(emailVerificationRequest: EmailVerificationRequest): ApiResponse<BaseResponse<String>> =
        executeSafely(call = { service.emailVerification(emailVerificationRequest) })

    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResponse<BaseResponse<String>> {
        val response = executeSafely(call = { service.signUp(signUpRequest) })
        when (response) {
            is ApiResponse.Success -> {
                CookiesManager.jwtToken = response.data.data
                CookiesManager.isLoggedIn = true
            }
            else -> {
                // to be handle later
            }
        }
        return response
    }

    override suspend fun postDemographicData(demographicDataRequest: DemographicDataRequest): ApiResponse<BaseApiResponse> =
        executeSafely(call = { service.postDemographicData(demographicDataRequest) })

    override suspend fun getAccountInfo(): ApiResponse<BaseListResponse<AccountInfo>> =
        executeSafely(call = { service.getAccountInfo() })

    override suspend fun getWaitingRanking(): ApiResponse<BaseResponse<WaitingListResponse>> =
        executeSafely(call = { service.getWaitingRanking() })

    override suspend fun saveReferralInvitation(saveReferralRequest: SaveReferralRequest): ApiResponse<BaseApiResponse> =
        executeSafely(call = { service.saveReferralInvitation(saveReferralRequest) })

    override suspend fun sendInviteFriend(sendInviteFriendRequest: SendInviteFriendRequest): ApiResponse<BaseApiResponse> =
        executeSafely(call = { service.sendInviteFriend(sendInviteFriendRequest) })

    override suspend fun verifyUser(user: String): ApiResponse<BaseResponse<Boolean>> =
        executeSafely(call = { service.verifyUser(user) })

    override suspend fun generateOTPForDeviceVerification(demographicDataRequest: DemographicDataRequest): ApiResponse<BaseApiResponse> =
        executeSafely(call = { service.generateOTPForDeviceVerification(demographicDataRequest) })

    override suspend fun verifyOTPForDeviceVerification(demographicDataRequest: DemographicDataRequest): ApiResponse<BaseResponse<String>> =
        executeSafely(call = { service.verifyOTPForDeviceVerification(demographicDataRequest) })

    override suspend fun completeVerification(completeVerificationRequest: CompleteVerificationRequest): ApiResponse<BaseApiResponse> =
        executeSafely(call = { service.completeVerification(completeVerificationRequest) })

    override suspend fun createNewPasscode(createNewPasscodeRequest: CreateNewPasscodeRequest): ApiResponse<BaseApiResponse> =
        executeSafely(call = { service.createNewPasscode(createNewPasscodeRequest) })

    override suspend fun getReferralAmount(): ApiResponse<BaseResponse<ReferralAmount>> =
        executeSafely(call = { service.getReferralAmount() })

    companion object {
        const val URL_SEND_VERIFICATION_EMAIL = "/customers/api/sign-up/email"
        const val URL_SIGN_UP = "/customers/api/profile"
        const val URL_POST_DEMOGRAPHIC_DATA = "/customers/api/demographics/"
        const val URL_ACCOUNT_INFO = "/customers/api/accounts"
        const val URL_GET_RANKING = "/customers/api/portal/fetch-ranking"
        const val URL_SEND_INVITE = "/customers/api/save-invite"
        const val URL_SAVE_REFERRAL_INVITATION = "/customers/api/save-referral-invitation"
        const val URL_VERIFY_USER = "/customers/api/verify-user"
        const val URL_POST_DEMOGRAPHIC_DATA_SIGN_IN = "/customers/api/demographics/device-login"
        const val URL_COMPLETE_VERIFICATION = "customers/api/v2/profile"
        const val URL_GET_DOCUMENTS_BY_TYPE = "/customers/api/document-information"
        const val URL_GET_DOCUMENT_DETAILS = "/customers/api/kyc/document-data"
        const val URL_UPLOAD_DOCUMENT = "/customers/api/v2/documents"
        const val URL_GET_MOTHER_NAMES = "/customers/api/getMotherMaidenNames"
        const val URL_GET_PLACE_OF_BIRTH_CITIES = "/customers/api/getCityOfBirthNames"
        const val URL_VERIFY_SECRET_QUESTIONS = "/customers/api/verifySecretQuestions"
        const val URL_UPLOAD_SELFIE = "/customers/api/customers/selfie-picture"
        const val URL_SAVE_CARD_NAME = "/customers/api/accounts/set-card-name"
        const val URL_CREATE_NEW_PASSCODE = "/customers/api/forgot-password"
        const val URL_GET_CITIES = "/customers/api/cities"
        const val URL_SAVE_ADDRESS_ORDERED_CARD = "/cards/api/save-address-and-order-card"
        const val URL_OCR_DETECT = "/digi-ocr/detect/"
        const val URL_VERIFY_PASSCODE = "/customers/api/user/verify-passcode"
        const val URL_GET_RECENT_TRANSFERS = "/customers/api/beneficiaries/recent"
        const val URL_GET_REFERRAL_AMOUNT = "/customers/api/get-referral-amount"
        const val URL_GET_Y2Y_USER = "/customers/api/y2y-beneficiaries"
        const val URL_GET_IBFT_BENEFICIARIES = "/customers/api/beneficiaries/bank-transfer"
        const val URL_GET_EXTERNAL_CARDS = "customers/api/mastercard/beneficiaries"
        const val URL_GET_ACCOUNT_BALANCE = "/customers/api/account/balance"
        const val URL_DELETE_EXTERNAL_CARD = "customers/api/mastercard/beneficiaries/{cardId}"
        const val URL_ADD_EXTERNAL_CARD = "customers/api/mastercard/beneficiaries"
        const val URL_ADD_EXTERNAL_CARD_PAYMENT = "customers/api/external-card/beneficiaries"
    }

}
