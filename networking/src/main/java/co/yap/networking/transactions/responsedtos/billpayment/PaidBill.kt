package co.yap.networking.transactions.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class PaidBill(
    @SerializedName("categoryName")
    var categpryName: String? = null,
    @SerializedName("PaymentStatus")
    var PaymentStatus: String? = null,
    @SerializedName("billerName")
    var billerName: String? = null,
    @SerializedName("logo")
    var logo: String? = null,
    @SerializedName("amount")
    var amount: String? = null
)
