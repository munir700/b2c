package co.yap.networking.authentication.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class MsTokenResponse(
    @SerializedName("errors")
    val errors: String? = null,
    @SerializedName("data")
    val data: String? = null
) : ApiResponse()