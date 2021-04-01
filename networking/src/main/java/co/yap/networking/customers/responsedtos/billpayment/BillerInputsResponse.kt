package co.yap.networking.customers.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class BillerInputsResponse(
    @SerializedName("SKU")
    var sku: String?,
    @SerializedName("BillerID")
    var billerID: String?,
    @SerializedName("Currency")
    var currency: String?,
    @SerializedName("BusinessDays")
    var businessDays: String?,
    @SerializedName("PastDuePaymentAllowed")
    var pastDuePaymentAllowed: String?,
    @SerializedName("Amount")
    var amount: String?,
    @SerializedName("MinAmount")
    var MinAmount: String?,
    @SerializedName("Description")
    var Description: String?,
    @SerializedName("InquiryAvailable")
    var inquiryAvailable: String?,
    @SerializedName("ExcessPaymentAllowed")
    var ExcessPaymentAllowed: String?,
    @SerializedName("PartialPaymentAllowed")
    var PartialPaymentAllowed: String?,
    @SerializedName("MaxAmount")
    var maxAmount: String?,
    @SerializedName("IoCatalogs")
    var ioCatalogs: List<IoCatalogsModel>?
)
