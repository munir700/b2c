package co.yap.networking.customers.responsedtos.employment_amendment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class DocumentResponse(
    @SerializedName("onChange")
    val onChange: String? = null,
    @SerializedName("from")
    val from: String? = null,
    @SerializedName("to")
    val to: String? = null,
    @SerializedName("employmentProofDocuments")
    val documents: List<Document> = listOf()
) : ApiResponse()