package co.yap.networking.customers.requestdtos

import com.google.gson.annotations.SerializedName

data class DemographicDataRequest(
    @SerializedName("action") val action: String,
    @SerializedName("osVersion") val osVersion: String,
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("deviceName") val deviceName: String,
    @SerializedName("deviceModel") val deviceModel: String,
    @SerializedName("osType") val osType: String,
    @SerializedName("token") val token: String? = null
)
