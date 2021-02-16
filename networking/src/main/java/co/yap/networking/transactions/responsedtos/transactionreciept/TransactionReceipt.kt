package co.yap.networking.transactions.responsedtos.transactionreciept

import com.google.gson.annotations.SerializedName

data class TransactionReceipt(
    @SerializedName("trxnReceiptList")
    val trxnReceiptList: List<String>? = null
)