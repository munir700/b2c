package co.yap.networking.onboarding.requestdtos

import com.google.gson.annotations.SerializedName

data class CreateOtpRequest(val countryCode: String, val mobileNo: String, @SerializedName("account_type") val accountType: String)
