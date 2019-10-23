package co.yap.networking.transactions.responsedtos.transaction

data class Pageable(
    var offset: Int,
    var pageNumber: Int,
    var pageSize: Int,
    var paged: Boolean,
    var sort: Sort,
    var unpaged: Boolean
)