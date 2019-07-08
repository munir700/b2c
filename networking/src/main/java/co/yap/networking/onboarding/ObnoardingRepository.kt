package co.yap.networking.onboarding

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.requestdtos.CreateOtpRequest
import co.yap.networking.onboarding.requestdtos.SendVerificationEmailRequest
import co.yap.networking.onboarding.requestdtos.SignUpRequest
import co.yap.networking.onboarding.requestdtos.VerifyOtpRequest
import co.yap.networking.onboarding.responsedtos.SignUpResponse

object ObnoardingRepository : BaseRepository(), OnboardingApi {

    // Onboarding URLS
    const val URL_CREATE_OTP = "/messages/api/otp/sign-up/mobile-no"
    const val URL_VERIFY_OTP = "/messages/api/otp/sign-up/verify"
    const val URL_SIGN_UP = "/customers/api/profile"
    const val URL_SEND_VERIFICATION_EMAIL = "/customers/api/sign-up/email"

    private val api: OnboardingRetroService = RetroNetwork.createService(OnboardingRetroService::class.java)

    override suspend fun createOtp(createOtpRequest: CreateOtpRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.createOtp(createOtpRequest) })

    override suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.verifyOtp(verifyOtpRequest) })

    override suspend fun signUp(signUpRequest: SignUpRequest): RetroApiResponse<SignUpResponse> =
        executeSafely(call = { api.signUp(signUpRequest) })

    override suspend fun sendVerificationEmail(verificationEmailRequest: SendVerificationEmailRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.sendVerificationEmail(verificationEmailRequest) })


}
