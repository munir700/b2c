package co.yap.networking.transactions.requestdtos


data class HomeTransactionsRequest(
    var number: Int,
    var size: Int,

    var amountStartRange: Double?,
    var amountEndRange: Double?,
    var creditSearch: Boolean?,
    var debitSearch: Boolean?,
    var totalAppliedFilter: Int,
    var yapYoungTransfer: Boolean?
)
