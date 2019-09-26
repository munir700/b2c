package co.yap.networking.transactions.responsedtos

import co.yap.networking.models.ApiResponse

class CardStatementsResponse(val data: List<CardStatement>) : ApiResponse()