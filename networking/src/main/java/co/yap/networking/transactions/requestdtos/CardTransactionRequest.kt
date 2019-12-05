package co.yap.networking.transactions.requestdtos

import com.google.gson.annotations.SerializedName

data class CardTransactionRequest(
    @SerializedName("number")
    var number: Int,
    @SerializedName("size")
    var size: Int,
    @SerializedName("serialNumber")
    var serialNumber: String
)
