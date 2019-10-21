package co.yap.networking.transactions.requestdtos


data class HomeTransactionsRequest(
    val minAmount: Double,
    val maxAmount: Double,
    val creditSearch: Boolean,
    val debitSearch: Boolean,
    val yapYoungTransfer: Boolean
)

//{{URL_TXN_MS}}/api/account-transactions/1/5?&minAmount=10.00&maxAmount=100.00&creditSearch=true&debitSearch=true&yapYoungTransfer=true