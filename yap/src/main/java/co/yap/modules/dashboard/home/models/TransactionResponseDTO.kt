package co.yap.modules.dashboard.home.models

import co.yap.networking.models.ApiResponse

data class TransactionResponseDTO( val data: ArrayList<TransactionModel>):ApiResponse() {
}