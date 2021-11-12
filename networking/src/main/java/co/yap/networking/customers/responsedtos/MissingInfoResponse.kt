package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class MissingInfoResponse(
    @SerializedName("data") val amendmentFields: List<AmendmentFields>
) : ApiResponse()

data class AmendmentFields(
    @SerializedName("sectionName") val sectionName: String,
    @SerializedName("amendments") val amendments: List<String>
)


