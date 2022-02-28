package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import com.google.gson.annotations.SerializedName

data class TotalPurchasesTransactionResponse(
    @SerializedName("data")
    val data : List<Transaction>
) : ApiResponse()


