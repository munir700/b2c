package com.yap.yappakistan.networking.microservices.messages.responsedtos
import com.google.gson.annotations.SerializedName
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse

class OtpValidationOnBoardingResponse(@SerializedName("data") var data: OtpValidation? = OtpValidation()) :
    BaseApiResponse()