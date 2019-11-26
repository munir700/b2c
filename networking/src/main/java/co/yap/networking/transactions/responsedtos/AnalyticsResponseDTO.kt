package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse

data class AnalyticsResponseDTO(
    val date: String,
    val totalTxnAmount: Double,
    val totalTxnCount: Double,
    val monthlyAvgAmount: Double,
    val txnAnalytics: List<TxnAnalytic>
) : ApiResponse()