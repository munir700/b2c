package co.yap.networking.transactions.responsedtos.transaction

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
)