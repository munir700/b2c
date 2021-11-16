package co.yap.networking.customers.responsedtos.BirthInfoAmendment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BirthInfoAmendmentResponse(
    @SerializedName("errors")
    val errors: String? = null,
    @SerializedName("data")
    val data: String? = null
) : ApiResponse()