package co.yap.networking.customers.responsedtos.taxinfoamendment


import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class TaxInfoAmendmentResponse(
    @SerializedName("isAmendment")
    val isAmendment: Any? = null,
    @SerializedName("submit")
    val submit: Boolean? = null,
    @SerializedName("taxInformationDetails")
    val taxInformationDetails: List<TaxInformationDetail?>? = null,
    @SerializedName("usNationalForTax")
    val usNationalForTax: Boolean? = null
) : ApiResponse() {
    data class TaxInformationDetail(
        @SerializedName("country")
        val country: String? = null,
        @SerializedName("customerId")
        val customerId: String? = null,
        @SerializedName("reasonInCaseNoTin")
        val reasonInCaseNoTin: String? = null,
        @SerializedName("tinAvailable")
        val tinAvailable: Boolean? = null,
        @SerializedName("tinNumber")
        val tinNumber: String? = null
    )
}