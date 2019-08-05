package co.yap.networking.cards

import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface CardsApi {
    suspend fun createCardPin(createCardPinRequest: CreateCardPinRequest, cardSerialNumber : String): RetroApiResponse<ApiResponse>

}