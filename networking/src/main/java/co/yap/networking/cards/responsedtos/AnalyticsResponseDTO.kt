package co.yap.networking.cards.responsedtos

import co.yap.networking.models.ApiResponse

data class AnalyticsResponseDTO(
    val date: String,
    val totalTxnAmount: Int,
    val totalTxnCount: Int,
    val txnAnalytics: List<TxnAnalytic>
) : ApiResponse()