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
    var minAmount: Double? = null,
    @SerializedName("description")
    var Description: String? = null,
    @SerializedName("inquiryAvailable")
    var inquiryAvailable: String? = null,
    @SerializedName("excessPaymentAllowed")
    var excessPaymentAllowed: Int? = null,
    @SerializedName("partialPaymentAllowed")
    var partialPaymentAllowed: Int? = null,
    @SerializedName("maxAmount")
    var maxAmount: Double? = null,
    @SerializedName("ioCatalogs")
    var ioCatalogs: List<IoCatalogModel>? = null,
    @SerializedName("isPostpaid")
    var isPostpaid: Int? = null,
    @SerializedName("isAirtime")
    var isAirtime: Boolean = false,
    @SerializedName("type")
    var type: String? = null
) : Parcelable {
    val isPrepaid: Boolean get() = isPostpaid == 0
    val isExcessPayment: Boolean get() = excessPaymentAllowed == 1
    val isPartialPayment: Boolean get() = partialPaymentAllowed == 1
}

