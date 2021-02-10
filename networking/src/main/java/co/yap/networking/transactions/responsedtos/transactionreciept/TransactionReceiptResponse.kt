package co.yap.networking.transactions.responsedtos.transactionreciept

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class TransactionReceiptResponse(
    @SerializedName("data")
    val data: TransactionReceipt? = null
) : ApiResponse()