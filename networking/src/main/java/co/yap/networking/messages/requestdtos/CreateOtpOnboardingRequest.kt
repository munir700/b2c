package co.yap.networking.messages.requestdtos

import com.google.gson.annotations.SerializedName

data class CreateOtpOnboardingRequest(val countryCode: String, val mobileNo: String, @SerializedName("account_type") val accountType: String)
