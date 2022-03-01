package com.yap.yappakistan.networking.microservices.messages


import com.yap.yappakistan.networking.apiclient.base.ApiResponse
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse
import com.yap.yappakistan.networking.apiclient.models.BaseResponse
import com.yap.yappakistan.networking.microservices.messages.requestdtos.CreateOtpOnboardingRequest
import com.yap.yappakistan.networking.microservices.messages.requestdtos.ForgotPasscodeOtpRequest
import com.yap.yappakistan.networking.microservices.messages.requestdtos.VerifyOtpOnboardingRequest
import com.yap.yappakistan.networking.microservices.messages.responsedtos.ForgotPasscodeOtpResponse
import com.yap.yappakistan.networking.microservices.messages.responsedtos.OtpValidationOnBoardingResponse
import com.yap.yappakistan.networking.microservices.messages.responsedtos.TermsAndConditionsResponse

interface MessagesApi {

    suspend fun createOtpOnboarding(createOtpOnboardingRequest: CreateOtpOnboardingRequest): ApiResponse<BaseApiResponse>
    suspend fun verifyOtpOnboarding(verifyOtpOnboardingRequest: VerifyOtpOnboardingRequest): ApiResponse<OtpValidationOnBoardingResponse>
    suspend fun getTerms(): ApiResponse<TermsAndConditionsResponse>
    suspend fun getHelpDesk(): ApiResponse<BaseResponse<String>>
    suspend fun createForgotPasscodeOTP(forgotPasscodeOtpRequest: ForgotPasscodeOtpRequest): ApiResponse<ForgotPasscodeOtpResponse>
    suspend fun verifyForgotPasscodeOTP(forgotPasscodeOtpRequest: ForgotPasscodeOtpRequest): ApiResponse<ForgotPasscodeOtpResponse>
}
