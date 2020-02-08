package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class DailyTransactionResponseDTO(@SerializedName("data") var data: DailyTransactionModel) : ApiResponse() {
}