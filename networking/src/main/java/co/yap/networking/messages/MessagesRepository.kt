package co.yap.networking.messages

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.messages.requestdtos.*
import co.yap.networking.messages.responsedtos.CreateForgotPasscodeOtpResponse
import co.yap.networking.messages.responsedtos.HelpDeskResponse
import co.yap.networking.messages.responsedtos.ValidateDeviceResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object MessagesRepository : BaseRepository(), MessagesApi {

    const val URL_CREATE_OTP_ONBOARDING = "/messages/api/otp/sign-up/mobile-no"
    const val URL_VERIFY_OTP_ONBOARDING = "/messages/api/otp/sign-up/verify"
    const val URL_CREATE_OTP_GENERIC = "/messages/api/otp"
    const val URL_VERIFY_OTP_GENERIC = "/messages/api/otp"
    const val URL_VERIFY_OTP_GENERIC_WITH_PHONE = "/messages/api/otp/{phone}"
    const val URL_FORGOT_PASSCODE = "/messages/api/otp/action/forgot-password"
    const val URL_VERIFY_FORGOT_PASSCODE_OTP = "/messages/api/otp/action/forgot-password"
    const val URL_HELP_DESK_PHONE = "/messages/api/help-desk"

    private val API: MessagesRetroService =
        RetroNetwork.createService(MessagesRetroService::class.java)

    override suspend fun createOtpOnboarding(createOtpOnboardingRequest: CreateOtpOnboardingRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { API.createOtpOnboarding(createOtpOnboardingRequest) })

    override suspend fun verifyOtpOnboarding(verifyOtpOnboardingRequest: VerifyOtpOnboardingRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { API.verifyOtpOnboarding(verifyOtpOnboardingRequest) })


    override suspend fun createOtpGeneric(createOtpGenericRequest: CreateOtpGenericRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.createOtpGeneric(createOtpGenericRequest) })

    override suspend fun verifyOtpGeneric(verifyOtpGenericRequest: VerifyOtpGenericRequest): RetroApiResponse<ValidateDeviceResponse> =
        AuthRepository.executeSafely(call = { API.verifyOtpGeneric(verifyOtpGenericRequest) })

    override suspend fun verifyOtpGenericWithPhone(
        phone: String,
        verifyOtpGenericRequest: VerifyOtpGenericRequest
    ): RetroApiResponse<ValidateDeviceResponse> =
        AuthRepository.executeSafely(call = { API.verifyOtpGenericWithPhone(phone,verifyOtpGenericRequest) })


    override suspend fun createForgotPasscodeOTP(createForgotPasscodeOtpRequest: CreateForgotPasscodeOtpRequest): RetroApiResponse<CreateForgotPasscodeOtpResponse> =
        AuthRepository.executeSafely(call = {
            API.createForgotPasscodeOTP(
                createForgotPasscodeOtpRequest
            )
        })

    override suspend fun verifyForgotPasscodeOtp(verifyForgotPasscodeOtpRequest: VerifyForgotPasscodeOtpRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = {
            API.verifyForgotPasscodeOtp(
                verifyForgotPasscodeOtpRequest
            )
        })

    override suspend fun getHelpDeskContact(): RetroApiResponse<HelpDeskResponse> =
        AuthRepository.executeSafely(call = { API.getHelpDeskContact() })
}
