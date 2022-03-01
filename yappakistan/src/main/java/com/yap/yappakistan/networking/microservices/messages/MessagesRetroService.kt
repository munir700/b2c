package com.yap.yappakistan.networking.microservices.messages

import com.yap.yappakistan.networking.apiclient.models.BaseResponse
import com.yap.yappakistan.networking.microservices.messages.requestdtos.CreateOtpOnboardingRequest
import com.yap.yappakistan.networking.microservices.messages.requestdtos.ForgotPasscodeOtpRequest
import com.yap.yappakistan.networking.microservices.messages.requestdtos.VerifyOtpOnboardingRequest
import com.yap.yappakistan.networking.microservices.messages.responsedtos.ForgotPasscodeOtpResponse
import com.yap.yappakistan.networking.microservices.messages.responsedtos.OtpValidationOnBoardingResponse
import com.yap.yappakistan.networking.microservices.messages.responsedtos.TermsAndConditionsResponse
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MessagesRetroService {
    // Create otp for mobile number
    @POST(MessagesRepository.URL_CREATE_OTP_ONBOARDING)
    suspend fun createOtpOnboarding(@Body createOtpOnboardingRequest: CreateOtpOnboardingRequest): Response<BaseApiResponse>

    // Verify otp for mobile number
    @PUT(MessagesRepository.URL_VERIFY_OTP_ONBOARDING)
    suspend fun verifyOtpOnboarding(@Body verifyOtpOnboardingRequest: VerifyOtpOnboardingRequest): Response<OtpValidationOnBoardingResponse>

    @GET(MessagesRepository.URL_TERMS_CONDITIONS)
    suspend fun getTerms(): Response<TermsAndConditionsResponse>

    @GET(MessagesRepository.URL_HELP_DESK)
    suspend fun getHelpDesk(): Response<BaseResponse<String>>

    //forgot passcode create otp
    @POST(MessagesRepository.URL_FORGOT_PASSCODE)
    suspend fun createForgotPasscodeOTP(@Body forgotPasscodeOtpRequest: ForgotPasscodeOtpRequest): Response<ForgotPasscodeOtpResponse>

    //forgot passcode verify otp
    @PUT(MessagesRepository.URL_FORGOT_PASSCODE)
    suspend fun verifyForgotPasscodeOTP(@Body forgotPasscodeOtpRequest: ForgotPasscodeOtpRequest): Response<ForgotPasscodeOtpResponse>

}