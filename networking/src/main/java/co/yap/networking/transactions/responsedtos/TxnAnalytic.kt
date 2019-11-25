package co.yap.networking.transactions.responsedtos

data class TxnAnalytic(
    val logoUrl: String?,
    val title: String,
    val totalSpending: String,
    val totalSpendingInPercentage: Double,
    val txnCount: Int
)