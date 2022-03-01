package com.yap.yappakistan.networking.microservices.messages.requestdtos


import com.google.gson.annotations.SerializedName

data class VerifyOtpOnboardingRequest(
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("mobileNo") val mobileNo: String,
    @SerializedName("otp") val otp: String
)
