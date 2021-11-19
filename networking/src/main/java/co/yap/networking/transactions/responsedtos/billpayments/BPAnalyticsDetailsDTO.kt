package co.yap.networking.transactions.responsedtos.billpayments

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BPAnalyticsDetailsDTO(
        @SerializedName("data")
        val data: BPAnalyticsDetailsResponse? = null
) : ApiResponse()
