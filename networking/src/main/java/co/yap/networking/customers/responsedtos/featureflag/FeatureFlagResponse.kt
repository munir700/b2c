package co.yap.networking.customers.responsedtos.featureflag

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class FeatureFlagResponse(
    @SerializedName("featureDtos") var featureDtos: ArrayList<FeatureDtos> = arrayListOf()
) : ApiResponse()

data class FeatureDtos(
    @SerializedName("key") var key: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("value") var value: Boolean
) : ApiResponse()