package co.yap.networking.models

import com.google.gson.annotations.SerializedName

data class ApiError (
    @SerializedName("status_code") var statusCode: Int,
    var message: String = "") {
}