package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse

class AddRemoveFundsResponse(val transactionId: String, val balance: String) : ApiResponse()