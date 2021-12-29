package co.yap.networking.transactions.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class BillHistory(
    @SerializedName("totalPaidAmount")
    var totalPaidAmount: String? = null,
    @SerializedName("currency")
    var currency: String? = null,
    @SerializedName("lastPayment")
    var lastPayment: PaymentStat? = null,
    @SerializedName("highestPayment")
    var highestPayment: PaymentStat? = null,
    @SerializedName("lowestPayment")
    var lowestPayment: PaymentStat? = null
)
