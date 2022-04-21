package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class SystemConfigurationInfoResponse(@SerializedName("data") val data: List<SystemConfigurationInfo>) : ApiResponse()