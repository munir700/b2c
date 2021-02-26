package co.yap.modules.dashboard.transaction.detail.models

data class TransactionDetail(
    val noteValue: String?,
    val categoryTitle: String?,
    val totalAmount: String?,
    val locationValue: String?,
    val transferType: String?,
    val categoryIcon: Int?,
    val statusIcon: Int?,
    val coverImage: Int?,
    val transactionItem: List<ItemTransactionDetail>
)


