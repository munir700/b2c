package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class CardsLimitResponse(
    @SerializedName("data")
    var data: Data? = null,
    @SerializedName("errors")
    var errors: Any? = null
): ApiResponse()