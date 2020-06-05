package co.yap.networking.household.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class ValidateParentMobileResponse(
    @SerializedName("data")
    val data: String? = null
) : ApiResponse()