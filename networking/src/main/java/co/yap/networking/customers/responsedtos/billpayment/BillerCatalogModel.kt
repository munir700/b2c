package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BillerCatalogModel(
    @SerializedName("billerID")
    var billerID: String?,
    @SerializedName("billerName")
    var billerName: String?,
    @SerializedName("billerType")
    var billerType: String?,
    @SerializedName("countryName")
    var countryName: String?,
    @SerializedName("countryCode")
    var countryCode: String?,
    @SerializedName("billerDescription")
    var billerDescription: String?,
    @SerializedName("logo")
    var logo: String?
) : ApiResponse()
