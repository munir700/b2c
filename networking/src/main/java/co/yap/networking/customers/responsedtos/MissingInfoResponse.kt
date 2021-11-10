package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class MissingInfoResponse(
    @SerializedName("customerId") val customerId: Int,
    @SerializedName("accountUuid") val accountUuid: String,
    @SerializedName("amendmentFields") val amendmentFields: List<AmendmentFields>
) : ApiResponse()

data class AmendmentFields(
    @SerializedName("sectionName") val sectionName: String,
    @SerializedName("amendments") val amendments: List<String>
)


