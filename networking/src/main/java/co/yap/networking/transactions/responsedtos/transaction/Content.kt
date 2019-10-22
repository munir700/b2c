package co.yap.networking.transactions.responsedtos.transaction

data class Content(
    var closingBalance: Double,
    var id: Int,
    var merchant: Any?,
    var paymentMode: String,
    var title: Any?,
    var txnAmount: Double,
    var txnCategory: String,
    var txnCurrency: String,
    var txnDate: String,
    var txnType: String
)
