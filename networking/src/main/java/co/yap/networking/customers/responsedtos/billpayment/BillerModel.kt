package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BillerModel(
    @SerializedName("BillerID")
    var billerID: String?,
    @SerializedName("BillerName")
    var billerName: String?,
    @SerializedName("BillerType")
    var billerType: String?,
    @SerializedName("CountryName")
    var countryName: String?,
    @SerializedName("CountryCode")
    var countryCode: String?,
    @SerializedName("BillerDescription")
    var billerDescription: String?,
    @SerializedName("logo")
    var logo: String?
) : ApiResponse()
