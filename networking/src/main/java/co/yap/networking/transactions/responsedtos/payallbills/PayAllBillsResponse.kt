package co.yap.networking.transactions.responsedtos.payallbills

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class PayAllBillsResponse(
    @SerializedName("data")
    val data: List<Data>? = null,
    @SerializedName("errors")
    val errors: Any? = null
) : ApiResponse()