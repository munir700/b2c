package co.yap.networking.customers.models.dashboardwidget

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class UpdateWidgetResponse (
    @SerializedName("errors") val errors : List<ErrorData>?,
    @SerializedName("data") val data : String
): ApiResponse()