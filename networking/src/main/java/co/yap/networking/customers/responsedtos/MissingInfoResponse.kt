package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class MissingInfoResponse(
    @SerializedName("data") val amendmentFields: List<AmendmentFields>
) : ApiResponse()

data class AmendmentFields(
    @SerializedName("sectionName") val sectionName: Section,
    @SerializedName("amendments") val amendments: List<String>
)

enum class Section(val value: String) {
    @SerializedName("eidInfo")
    EID_INFO("eidInfo"),

    @SerializedName("birthInfo")
    BIRTH_INFO("birthInfo"),

    @SerializedName("taxInfo")
    TAX_INFO("taxInfo"),

    @SerializedName("empInfo")
    EMPLOYMENT_INFO("empInfo")
}