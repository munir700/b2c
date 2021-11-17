package co.yap.networking.customers.responsedtos.BirthInfoAmendment


import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BirthInfoAmendmentResponse(
    @SerializedName("selectedCountry")
    val selectedCountry: Int? = null,
    @SerializedName("cityOfBirth")
    val cityOfBirth: String? = null,
    @SerializedName("selectedSecondCountry")
    val selectedSecondCountry: Int? = null,
    @SerializedName("cityOfBirth")
    val eidNationality: Int? = null,
    @SerializedName("isDualNational")
    val isDualNational: Boolean? = null
) : ApiResponse()