package co.yap.networking.transactions.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class PaymentStat(
    @SerializedName("billAmount")
    var billAmount: String? = null,
    @SerializedName("month")
    var month: String? = null
)
