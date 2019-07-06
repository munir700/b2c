package co.yap.networking.onboarding.requestdtos

import com.google.gson.annotations.SerializedName

data class SendVerificationEmailRequest(val email: String, @SerializedName("account_type") val accountType: String)
