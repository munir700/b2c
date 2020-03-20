package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class SubAccountInvitationResponse(
    @SerializedName("data") var data: String?=null,
    @SerializedName("errors") var errors: Any?
) : ApiResponse()