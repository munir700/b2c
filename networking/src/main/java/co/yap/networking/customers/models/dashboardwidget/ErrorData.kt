package co.yap.networking.customers.models.dashboardwidget

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message : String
)