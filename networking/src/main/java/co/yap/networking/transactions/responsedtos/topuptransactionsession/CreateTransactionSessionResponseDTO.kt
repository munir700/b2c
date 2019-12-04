package co.yap.networking.transactions.responsedtos.topuptransactionsession

import co.yap.networking.models.ApiResponse
import co.yap.networking.transactions.responsedtos.topuptransactionsession.CreateSessionResponseObject

data class CreateTransactionSessionResponseDTO(val data: CreateSessionResponseObject) :
    ApiResponse()