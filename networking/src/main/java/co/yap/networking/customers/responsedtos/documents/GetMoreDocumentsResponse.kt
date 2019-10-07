package co.yap.networking.customers.responsedtos.documents

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName


data class GetMoreDocumentsResponse (

    @SerializedName("errors") val errors : String,
    @SerializedName("data") val data : Data
) : ApiResponse()