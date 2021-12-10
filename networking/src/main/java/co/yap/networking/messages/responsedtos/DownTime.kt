package co.yap.networking.messages.responsedtos


import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class DownTime(
    @SerializedName("downTimeMessage")
    val downTimeMessage: String?="",
    @SerializedName("down")
    val isDown: Boolean?=false
):ApiResponse()