package co.yap.networking.cards

import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface CardsApi {
    suspend fun createCardPin(
        createCardPinRequest: CreateCardPinRequest,
        cardSerialNumber: String
    ): RetroApiResponse<ApiResponse>

    suspend fun orderCard(
        orderCardRequest: OrderCardRequest,
        nearestLandMark: String?,
        cardName: String?,
        address1: String?,
        latitude: Int?,
        longitude: Int?
    ): RetroApiResponse<ApiResponse>

}

