package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class CustomerInfoResponse(
    @SerializedName("data")
    var cardLimits: Customer? = null,
    @SerializedName("errors")
    var errors: Any? = null
): ApiResponse()