package com.yap.yappakistan.networking.microservices.customers

import com.yap.yappakistan.networking.apiclient.base.ApiResponse
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse
import com.yap.yappakistan.networking.apiclient.models.BaseListResponse
import com.yap.yappakistan.networking.apiclient.models.BaseResponse
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.DemographicDataRequest
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.*
import com.yap.yappakistan.networking.microservices.customers.responsedtos.ReferralAmount
import com.yap.yappakistan.networking.microservices.customers.responsedtos.WaitingListResponse
import com.yap.yappakistan.networking.microservices.customers.responsedtos.accountinfo.AccountInfo

interface CustomersApi {
    suspend fun emailVerification(emailVerificationRequest: EmailVerificationRequest): ApiResponse<BaseResponse<String>>
    suspend fun signUp(signUpRequest: SignUpRequest): ApiResponse<BaseResponse<String>>
    suspend fun postDemographicData(demographicDataRequest: DemographicDataRequest): ApiResponse<BaseApiResponse>
    suspend fun getAccountInfo(): ApiResponse<BaseListResponse<AccountInfo>>
    suspend fun getWaitingRanking(): ApiResponse<BaseResponse<WaitingListResponse>>
    suspend fun saveReferralInvitation(saveReferralRequest: SaveReferralRequest): ApiResponse<BaseApiResponse>
    suspend fun sendInviteFriend(sendInviteFriendRequest: SendInviteFriendRequest): ApiResponse<BaseApiResponse>
    suspend fun verifyUser(user: String): ApiResponse<BaseResponse<Boolean>>
    suspend fun generateOTPForDeviceVerification(demographicDataRequest: DemographicDataRequest): ApiResponse<BaseApiResponse>
    suspend fun verifyOTPForDeviceVerification(demographicDataRequest: DemographicDataRequest): ApiResponse<BaseResponse<String>>
    suspend fun completeVerification(completeVerificationRequest: CompleteVerificationRequest): ApiResponse<BaseApiResponse>
    suspend fun createNewPasscode(createNewPasscodeRequest: CreateNewPasscodeRequest): ApiResponse<BaseApiResponse>
    suspend fun getReferralAmount(): ApiResponse<BaseResponse<ReferralAmount>>
}
