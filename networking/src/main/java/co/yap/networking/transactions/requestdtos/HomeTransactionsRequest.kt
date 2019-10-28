package co.yap.networking.transactions.requestdtos


data class HomeTransactionsRequest(
    val number: Int,
    val size: Int,

    val minAmount: Double,
    val maxAmount: Double,
    val creditSearch: Boolean,
    val debitSearch: Boolean,
    val yapYoungTransfer: Boolean
)
