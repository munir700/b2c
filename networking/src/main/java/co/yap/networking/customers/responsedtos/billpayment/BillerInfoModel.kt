package co.yap.networking.customers.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class BillerInfoModel(
    @SerializedName("billerType")
    var billerType: String,
    @SerializedName("countryName")
    var countryName: String,
    @SerializedName("countryCode")
    var countryCode: String,
    @SerializedName("billerDescription")
    var billerDescription: String,
    @SerializedName("billerName")
    var billerName: String,
    @SerializedName("billerID")
    var billerID: String,
    @SerializedName("creationDate")
    var creationDate: String?,
    @SerializedName("currency")
    var currency: String?,
    @SerializedName("logo")
    var logo: String?,
    @SerializedName("skuInfos")
    var skuInfos: BillerInputDetails?
)
