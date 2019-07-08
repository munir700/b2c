package co.yap.networking.onboarding

import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.requestdtos.CreateOtpRequest
import co.yap.networking.onboarding.requestdtos.SendVerificationEmailRequest
import co.yap.networking.onboarding.requestdtos.SignUpRequest
import co.yap.networking.onboarding.requestdtos.VerifyOtpRequest
import co.yap.networking.onboarding.responsedtos.AccountInfoResponse
import co.yap.networking.onboarding.responsedtos.SignUpResponse

interface OnboardingApi {
    suspend fun createOtp(createOtpRequest: CreateOtpRequest): RetroApiResponse<ApiResponse>
    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): RetroApiResponse<ApiResponse>
    suspend fun signUp(signUpRequest: SignUpRequest): RetroApiResponse<SignUpResponse>
    suspend fun sendVerificationEmail(verificationEmailRequest: SendVerificationEmailRequest): RetroApiResponse<ApiResponse>
    suspend fun getAccountInfo(): RetroApiResponse<AccountInfoResponse>

}