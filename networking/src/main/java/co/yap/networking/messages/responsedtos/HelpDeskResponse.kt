package co.yap.networking.messages.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class HelpDeskResponse : ApiResponse() {
    @SerializedName("data") var data: String? = ""
}