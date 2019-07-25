package co.yap.networking.messages

import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.messages.requestdtos.CreateOtpOnboardingRequest
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.messages.requestdtos.VerifyOtpOnboardingRequest
import co.yap.networking.messages.responsedtos.ValidateDeviceResponse
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface MessagesRetroService {

    // Create otp for mobile number
    @POST(MessagesRepository.URL_CREATE_OTP_ONBOARDING)
    suspend fun createOtpOnboarding(@Body createOtpOnboardingRequest: CreateOtpOnboardingRequest): Response<ApiResponse>

    // Verify otp for mobile number
    @PUT(MessagesRepository.URL_VERIFY_OTP_ONBOARDING)
    suspend fun verifyOtpOnboarding(@Body verifyOtpOnboardingRequest: VerifyOtpOnboardingRequest): Response<ApiResponse>

    // Create otp request
    @POST(MessagesRepository.URL_CREATE_OTP_GENERIC)
    suspend fun createOtpGeneric(@Body createOtpGenericRequest: CreateOtpGenericRequest): Response<ApiResponse>

    // Verify otp request
    @PUT(MessagesRepository.URL_VERIFY_OTP_GENERIC)
    suspend fun verifyOtpGeneric(@Body verifyOtpGenericRequest: VerifyOtpGenericRequest): Response<ValidateDeviceResponse>
}