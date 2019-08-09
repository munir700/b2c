package co.yap.networking.cards.responsedtos

import co.yap.networking.models.ApiResponse


data class GetCardsResponse(val data: ArrayList<Card>) : ApiResponse()
