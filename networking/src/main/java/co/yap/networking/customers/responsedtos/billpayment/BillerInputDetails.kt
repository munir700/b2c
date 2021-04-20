package co.yap.networking.customers.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class BillerInputDetails(
    @SerializedName("skuId")
    var skuId: String?,
    @SerializedName("billerID")
    var billerID: String?,
    @SerializedName("daysToPost")
    var daysToPost: String?,
    @SerializedName("currency")
    var currency: String? = null,
    @SerializedName("businessDays")
    var businessDays: String?,
    @SerializedName("pastDuePaymentAllowed")
    var pastDuePaymentAllowed: String?,
    @SerializedName("amount")
    var amount: String?,
    @SerializedName("minAmount")
    var MinAmount: String?,
    @SerializedName("description")
    var Description: String?,
    @SerializedName("inquiryAvailable")
    var inquiryAvailable: String?,
    @SerializedName("excessPaymentAllowed")
    var excessPaymentAllowed: String?,
    @SerializedName("partialPaymentAllowed")
    var partialPaymentAllowed: String?,
    @SerializedName("maxAmount")
    var maxAmount: String?,
    @SerializedName("ioCatalogs")
    var ioCatalogs: List<IoCatalogModel>?,
    @SerializedName("isPostpaid")
    var isPostpaid: String? = null,
    @SerializedName("catalogVersion")
    var catalogVersion: String? = null
)
