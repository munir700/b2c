package co.yap.networking.transactions.requestdtos


data class HomeTransactionsRequest(
    var number: Int,
    var size: Int,

    var amountStartRange: Double?,
    var amountEndRange: Double?,
    var txnType: String?,
    var title: String?,
    var totalAppliedFilter: Int
)
