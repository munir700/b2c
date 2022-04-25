package co.yap.networking.leanteach.requestdtos

import com.google.gson.annotations.SerializedName

data class GetPaymentIntentIdModel(
    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("customer_id") var customerId: String? = null,
    @SerializedName("payment_destination_id") var paymentDestinationId: String? = null
)