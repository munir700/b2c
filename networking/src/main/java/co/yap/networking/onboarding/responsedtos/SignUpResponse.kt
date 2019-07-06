package co.yap.networking.onboarding.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class SignUpResponse(val token: String, val iban: String) : ApiResponse()