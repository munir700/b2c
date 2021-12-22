package co.yap.networking.customers.requestdtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import java.io.File

data class PassportRequest(
    @SerializedName("files") val files: File? = null,
    @SerializedName("passportNumber") val passportNumber: String? = null,
    @SerializedName("passportIssueDate") val passportIssueDate: String? = null,
    @SerializedName("passportExpiryDate") val passportExpiryDate: String? = null,
    @Transient val contentType: String? = null
) : ApiResponse()
