package co.yap.networking.transactions.responsedtos.transaction

import co.yap.networking.models.ApiResponse

data class HomeTransactionsResponse(
    var data: HomeTransactionListData,
    var errors: Any?
) : ApiResponse() {
    data class HomeTransactionListData(
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