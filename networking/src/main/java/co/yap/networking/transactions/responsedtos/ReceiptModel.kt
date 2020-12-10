package co.yap.networking.transactions.responsedtos

import com.google.gson.annotations.SerializedName

data class ReceiptModel(
    @SerializedName("receipt_name")
    val title: String? = null,
    @SerializedName("receipt_image_url")
    var receiptImageUrl: String? = null
)
