package co.yap.networking.transactions.responsedtos.transaction

import co.yap.networking.models.ApiResponse

data class HomeTransactionsResponse(
    var data: HomeTransactionListData,
    var errors: Any?
) : ApiResponse()