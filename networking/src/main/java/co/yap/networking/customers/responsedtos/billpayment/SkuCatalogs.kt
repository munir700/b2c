package co.yap.networking.customers.responsedtos.billpayment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkuCatalogs(
    @SerializedName("skuId")
    var skuId: String? = null,
    @SerializedName("billerID")
    var billerID: String? = null,
    @SerializedName("daysToPost")
    var daysToPost: String? = null,
    @SerializedName("currency")
    var currency: String? = null,
    @SerializedName("businessDays")
    var businessDays: String? = null,
    @SerializedName("pastDuePaymentAllowed")
    var pastDuePaymentAllowed: String? = null,
    @SerializedName("amount")
    var amount: String? = null,
    @SerializedName("minAmount")
    var MinAmount: String? = null,
    @SerializedName("description")
    var Description: String? = null,
    @SerializedName("inquiryAvailable")
    var inquiryAvailable: String? = null,
    @SerializedName("excessPaymentAllowed")
    var excessPaymentAllowed: String? = null,
    @SerializedName("partialPaymentAllowed")
    var partialPaymentAllowed: String? = null,
    @SerializedName("maxAmount")
    var maxAmount: String? = null,
    @SerializedName("ioCatalogs")
    var ioCatalogs: List<IoCatalogModel>? = null,
    @SerializedName("isPostpaid")
    var isPostpaid: String? = null,
    @SerializedName("type")
    var type: String? = null
) : Parcelable

