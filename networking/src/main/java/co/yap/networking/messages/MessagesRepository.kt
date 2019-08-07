package co.yap.networking.messages

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.messages.requestdtos.*
import co.yap.networking.messages.responsedtos.ValidateDeviceResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object MessagesRepository : BaseRepository(), MessagesApi {

    const val URL_CREATE_OTP_ONBOARDING = "/messages/api/otp/sign-up/mobile-no"
    const val URL_VERIFY_OTP_ONBOARDING = "/messages/api/otp/sign-up/verify"
    const val URL_CREATE_OTP_GENERIC = "/messages/api/otp"
    const val URL_VERIFY_OTP_GENERIC = "/messages/api/otp"
    const val URL_FORGOT_PASSCODE="/messages/api/otp/action/forgot-password"

    private val API: MessagesRetroService = RetroNetwork.createService(MessagesRetroService::class.java)

    override suspend fun createOtpOnboarding(createOtpOnboardingRequest: CreateOtpOnboardingRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { API.createOtpOnboarding(createOtpOnboardingRequest) })

    override suspend fun verifyOtpOnboarding(verifyOtpOnboardingRequest: VerifyOtpOnboardingRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { API.verifyOtpOnboarding(verifyOtpOnboardingRequest) })


    override suspend fun createOtpGeneric(createOtpGenericRequest: CreateOtpGenericRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.createOtpGeneric(createOtpGenericRequest) })

    override suspend fun verifyOtpGeneric(verifyOtpGenericRequest: VerifyOtpGenericRequest): RetroApiResponse<ValidateDeviceResponse> =
        AuthRepository.executeSafely(call = { API.verifyOtpGeneric(verifyOtpGenericRequest) })


    override suspend fun createForgotPasscodeOTP(createForgotPasscodeOtp: CreateForgotPasscodeOtp): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.createForgotPasscodeOTP(createForgotPasscodeOtp) })
}
