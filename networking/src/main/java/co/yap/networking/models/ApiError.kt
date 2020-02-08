package co.yap.networking.models

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("code") var statusCode: Int,
    @SerializedName("message") var message: String = "",
    @SerializedName("actualCode") var actualCode: Int = -1
)