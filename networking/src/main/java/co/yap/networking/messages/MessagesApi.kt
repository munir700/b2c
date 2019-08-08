package co.yap.networking.messages


import co.yap.networking.messages.requestdtos.*
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface MessagesApi {
    suspend fun createOtpOnboarding(createOtpOnboardingRequest: CreateOtpOnboardingRequest): RetroApiResponse<ApiResponse>
    suspend fun verifyOtpOnboarding(verifyOtpOnboardingRequest: VerifyOtpOnboardingRequest): RetroApiResponse<ApiResponse>
    suspend fun createOtpGeneric(createOtpGenericRequest: CreateOtpGenericRequest): RetroApiResponse<ApiResponse>
    suspend fun verifyOtpGeneric(verifyOtpGenericRequest: VerifyOtpGenericRequest): RetroApiResponse<ApiResponse>
    suspend fun createForgotPasscodeOTP(createForgotPasscodeOtp: CreateForgotPasscodeOtp): RetroApiResponse<ApiResponse>
}