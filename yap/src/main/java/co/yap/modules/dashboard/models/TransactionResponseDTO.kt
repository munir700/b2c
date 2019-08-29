package co.yap.modules.dashboard.models

import co.yap.networking.models.ApiResponse

data class TransactionResponseDTO( val data: ArrayList<TransactionModel>):ApiResponse() {
}