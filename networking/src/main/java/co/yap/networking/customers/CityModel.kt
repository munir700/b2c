package co.yap.networking.customers


import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("data")
    var data: ArrayList<String>? = ArrayList(),
    @SerializedName("errors")
    var errors: Any?
) : ApiResponse()