package co.yap.networking.transactions.responsedtos.billpayments

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BPAnalyticsDetailsResponse(
        @SerializedName("billFluctuation")
        val fluctuation: String? = null,
        @SerializedName("billData")
        val bills: List<AnalyticsBill>? = null
) : ApiResponse()
