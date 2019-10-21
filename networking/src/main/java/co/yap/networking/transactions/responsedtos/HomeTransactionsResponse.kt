package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse

data class HomeTransactionsResponse(
    var data: List<Data>,
    var errors: Any?
)  : ApiResponse() {
    data class Data(
        var closingBalance: Int,
        var id: Int,
        var merchant: Any?,
        var paymentMode: String,
        var title: Any?,
        var txnAmount: Int,
        var txnCategory: String,
        var txnCurrency: String,
        var txnDate: String,
        var txnType: String
    )
}