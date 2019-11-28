package co.yap.networking.transactions.responsedtos

data class TransactionAnalyticsDTO (
    val totalTxnCount: Int?,
    val totalTxnAmount: Double?,
    val monthlyAvgAmount: Double?,
    val date: String?,
    val txnAnalytics: ArrayList<TxnAnalytic>? = ArrayList()
)