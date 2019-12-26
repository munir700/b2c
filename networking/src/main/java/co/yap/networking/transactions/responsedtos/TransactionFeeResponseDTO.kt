package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse

data class TransactionFeeResponseDTO(
    var data: String
) : ApiResponse()