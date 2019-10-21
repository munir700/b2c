package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse

data class HomeTransactionsResponse(
    var data: Data,
    var errors: Any?
) : ApiResponse() {
    data class Data(
        var content: List<Content>,
        var first: Boolean,
        var last: Boolean,
        var number: Int,
        var numberOfElements: Int,
        var pageable: Pageable,
        var size: Int,
        var sort: Sort,
        var totalElements: Int,
        var totalPages: Int
    ) {
        data class Content(
            var closingBalance: Double,
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

        data class Pageable(
            var offset: Int,
            var pageNumber: Int,
            var pageSize: Int,
            var paged: Boolean,
            var sort: Sort,
            var unpaged: Boolean
        ) {
            data class Sort(
                var sorted: Boolean,
                var unsorted: Boolean
            )
        }

        data class Sort(
            var sorted: Boolean,
            var unsorted: Boolean
        )
    }
}