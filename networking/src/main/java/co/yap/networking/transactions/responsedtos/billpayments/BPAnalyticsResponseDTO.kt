package co.yap.networking.transactions.responsedtos.billpayments

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BPAnalyticsResponseDTO(
        @SerializedName("data")
        val data: List<BPAnalyticsModel>? = null
) : ApiResponse()
