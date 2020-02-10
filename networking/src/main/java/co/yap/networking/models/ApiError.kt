package co.yap.networking.models

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("code") var statusCode: Int,
    var message: String = "",
    var actualCode: String = "-1"
)