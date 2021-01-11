package co.yap.networking.authentication.requestdtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class MsTokenRequest(
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("device_id")
    val device_id: String? = null,
    @SerializedName("device_name")
    val device_name: String? = null,
    @SerializedName("os_type")
    val os_type: String? = null,
    @SerializedName("os_version")
    val os_version: String? = null
) : ApiResponse()