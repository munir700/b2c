package co.yap.networking.authentication.responsedtos

import co.yap.networking.models.ApiError
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class KycResponse(
    @SerializedName("Data")
    val data: Data?,
    @SerializedName("error")
    var errors: ApiError?
) : ApiResponse() {
    data class Data(
        val check_composite: String,
        val check_date_of_birth: String,
        val check_expiration_date: String,
        val check_number: String,
        val country: String,
        val date_of_birth: String,
        val expiration_date: String,
        val method: String,
        val mrz_type: String,
        val names: String,
        val nationality: String,
        val number: String,
        val optional1: String,
        val optional2: String,
        val sex: String?=null,
        val success: Boolean,
        val surname: String,
        val type: String,
        val valid_composite: Boolean,
        val valid_date_of_birth: Boolean,
        val valid_expiration_date: Boolean,
        val valid_number: Boolean,
        val valid_score: Int
    ) : ApiResponse()
}