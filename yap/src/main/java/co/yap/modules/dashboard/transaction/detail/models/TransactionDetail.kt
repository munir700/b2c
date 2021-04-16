package co.yap.modules.dashboard.transaction.detail.models

data class TransactionDetail(
    val transactionTitle: String?,
    val noteValue: String?,
    val noteAddedDate: String?,
    val categoryTitle: String?,
    val totalAmount: Double?,
    val locationValue: String?,
    val transferType: String?,
    val categoryIcon: Int?,
    val statusIcon: Int?,
    val coverImage: Int,
    val transactionItem: List<ItemTransactionDetail>,
    val showTotalPurchase: Boolean?,
    val showError: Boolean?,
    val showReceipts: Boolean?,
    val isAtmTransaction: Boolean?,
    val showCategory: Boolean?
)


