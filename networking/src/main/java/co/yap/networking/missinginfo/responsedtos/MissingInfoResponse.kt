package co.yap.networking.missinginfo.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class MissingInfoResponse(
    @SerializedName("data")
    val data: ArrayList<String>? = null
) : ApiResponse()
