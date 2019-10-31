package co.yap.networking.transactions.responsedtos.transaction


data class HomeTransactionListData(
    var type: String,
    var totalAmountType: String,
    var date: String,
    var totalAmount: String,
    var closingBalance: Double,
    var amountPercentage: Double,

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