package co.yap.networking.customers.responsedtos.billpayment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BillerInfoModel(
    @SerializedName("billerType")
    var billerType: String? = null,
    @SerializedName("countryName")
    var countryName: String? = null,
    @SerializedName("countryCode")
    var countryCode: String? = null,
    @SerializedName("billerDescription")
    var billerDescription: String? = null,
    @SerializedName("billerName")
    var billerName: String? = null,
    @SerializedName("billerID")
    var billerID: String? = null,
    @SerializedName("logo")
    var logo: String? = null,
    @SerializedName("categoryId")
    var categoryId: String? = null,
    @SerializedName("skuCatalogs")
    var skuInfos: List<SkuCatalogs>? = null
) : Parcelable

