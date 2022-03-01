package com.yap.yappakistan.networking.microservices.messages.responsedtos

import com.google.gson.annotations.SerializedName
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse

class ForgotPasscodeOtpResponse : BaseApiResponse() {
    @SerializedName("data")
    var data: String? = ""
}