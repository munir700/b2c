package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse

data class DailyTransactionResponseDTO(var data: DailyTransactionModel) : ApiResponse() {
}