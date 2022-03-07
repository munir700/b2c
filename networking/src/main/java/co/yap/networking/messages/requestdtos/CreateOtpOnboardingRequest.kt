package co.yap.networking.messages.requestdtos

import com.google.gson.annotations.SerializedName

data class CreateOtpOnboardingRequest(
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("mobileNo") val mobileNo: String,
    @SerializedName("account_type") val accountType: String,//only this line was already serialized
    @SerializedName("message") val otpMessage: String?=null//otp message for On-boarding number verification
)
