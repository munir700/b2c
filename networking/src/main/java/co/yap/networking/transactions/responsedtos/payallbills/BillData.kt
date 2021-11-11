package co.yap.networking.transactions.responsedtos.payallbills

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BillData(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("value")
    val value: String? = null
) : ApiResponse()