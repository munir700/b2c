package co.yap.networking.transactions.responsedtos.transactionreciept

import co.yap.networking.transactions.responsedtos.ReceiptModel
import com.google.gson.annotations.SerializedName

data class TransactionReceipt(
    @SerializedName("trxnReceiptList")
    val trxnReceiptList: List<String>? = null
)