package com.yap.yappakistan.networking.microservices.customers

import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse
import com.yap.yappakistan.networking.apiclient.models.BaseListResponse
import com.yap.yappakistan.networking.apiclient.models.BaseResponse
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.DemographicDataRequest
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.*
import com.yap.yappakistan.networking.microservices.customers.responsedtos.ReferralAmount
import com.yap.yappakistan.networking.microservices.customers.responsedtos.WaitingListResponse
import com.yap.yappakistan.networking.microservices.customers.responsedtos.accountinfo.AccountInfo
import retrofit2.Response
import retrofit2.http.*

interface CustomersRetroService {

    // In onboarding send verification email to verify user
    @POST(CustomersRepository.URL_SEND_VERIFICATION_EMAIL)
    suspend fun emailVerification(@Body emailVerificationRequest: EmailVerificationRequest): Response<BaseResponse<String>>

    // Save user profile
    @POST(CustomersRepository.URL_SIGN_UP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<BaseResponse<String>>

    // Post demographic dataList
    @PUT(CustomersRepository.URL_POST_DEMOGRAPHIC_DATA)
    suspend fun postDemographicData(@Body demographicDataRequest: DemographicDataRequest): Response<BaseApiResponse>

    // Get user account(s) Info
    @GET(CustomersRepository.URL_ACCOUNT_INFO)
    suspend fun getAccountInfo(): Response<BaseListResponse<AccountInfo>>

    // Get user waiting list ranking
    @GET(CustomersRepository.URL_GET_RANKING)
    suspend fun getWaitingRanking(): Response<BaseResponse<WaitingListResponse>>

    // Invite a friend
    @POST(CustomersRepository.URL_SEND_INVITE)
    suspend fun sendInviteFriend(@Body sendInviteFriendRequest: SendInviteFriendRequest): Response<BaseApiResponse>

    // Save Referral
    @POST(CustomersRepository.URL_SAVE_REFERRAL_INVITATION)
    suspend fun saveReferralInvitation(@Body saveReferralRequest: SaveReferralRequest): Response<BaseApiResponse>

    // Verify user
    @POST(CustomersRepository.URL_VERIFY_USER)
    suspend fun verifyUser(@Query("username") username: String): Response<BaseResponse<Boolean>>

    // Send Otp for device validate
    @POST(CustomersRepository.URL_POST_DEMOGRAPHIC_DATA_SIGN_IN)
    suspend fun generateOTPForDeviceVerification(@Body demographicDataRequest: DemographicDataRequest): Response<BaseResponse<BaseApiResponse>>

    // Verify OTP for device validate
    @PUT(CustomersRepository.URL_POST_DEMOGRAPHIC_DATA_SIGN_IN)
    suspend fun verifyOTPForDeviceVerification(@Body demographicDataRequest: DemographicDataRequest): Response<BaseResponse<String>>

    // Assign IBAN for complete verification
    @POST(CustomersRepository.URL_COMPLETE_VERIFICATION)
    suspend fun completeVerification(@Body completeVerificationRequest: CompleteVerificationRequest): Response<BaseApiResponse>

    //create new passcode
    @PUT(CustomersRepository.URL_CREATE_NEW_PASSCODE)
    suspend fun createNewPasscode(@Body createNewPasscodeRequest: CreateNewPasscodeRequest): Response<BaseApiResponse>

    // Get referral amount
    @GET(CustomersRepository.URL_GET_REFERRAL_AMOUNT)
    suspend fun getReferralAmount(): Response<BaseResponse<ReferralAmount>>

}