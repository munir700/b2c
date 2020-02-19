package co.yap.networking.admin.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class AppUpdateResponse(
    @SerializedName("data")
    val data: List<AppUpdate>? = arrayListOf()
): ApiResponse()