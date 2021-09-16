package co.yap.networking.transactions.responsedtos.categorybar

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class CategoryBarResponse(
    @SerializedName("errors") val errors: String,
    @SerializedName("data") val categoryBarData: CategoryBarData
) : ApiResponse()