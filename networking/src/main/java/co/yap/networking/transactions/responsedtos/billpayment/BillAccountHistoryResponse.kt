package co.yap.networking.transactions.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BillAccountHistoryResponse(
    @SerializedName("data")
    val billHistory: BillHistory? = null
) : ApiResponse()
