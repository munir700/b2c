package co.yap.networking.onboarding

import co.yap.networking.models.ApiResponse
import co.yap.networking.onboarding.requestdtos.CreateOtpRequest
import co.yap.networking.onboarding.requestdtos.SendVerificationEmailRequest
import co.yap.networking.onboarding.requestdtos.SignUpRequest
import co.yap.networking.onboarding.requestdtos.VerifyOtpRequest
import co.yap.networking.onboarding.responsedtos.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface OnboardingRetroService {

    // Create otp for mobile number
    @POST(ObnoardingRepository.URL_CREATE_OTP)
    suspend fun createOtp(@Body createOtpRequest: CreateOtpRequest): Response<ApiResponse>

    // Verify otp for mobile number
    @PUT(ObnoardingRepository.URL_VERIFY_OTP)
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<ApiResponse>

    // User sign up request
    @POST(ObnoardingRepository.URL_SIGN_UP)
    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse>

    // In onboarding send verification email to verify uer
    @POST(ObnoardingRepository.URL_SEND_VERIFICATION_EMAIL)
    suspend fun sendVerificationEmail(sendVerificationEmailRequest: SendVerificationEmailRequest): Response<ApiResponse>

}