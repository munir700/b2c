package co.yap.networking.cards

import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.responsedtos.DebitCardBalanceResponseDTO
import co.yap.networking.cards.responsedtos.GetCardsResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

interface CardsApi {
    suspend fun createCardPin(
        createCardPinRequest: CreateCardPinRequest,
        cardSerialNumber: String
    ): RetroApiResponse<ApiResponse>

    suspend fun getDebitCards(cardType: String): RetroApiResponse<GetCardsResponse>
    suspend fun orderCard(
        orderCardRequest: OrderCardRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun getAccountBalanceRequest():RetroApiResponse<DebitCardBalanceResponseDTO>
    suspend fun configAllowAtm(cardSerialNumber: String): RetroApiResponse<ApiResponse>
    suspend fun configAbroadPayment(cardSerialNumber: String): RetroApiResponse<ApiResponse>
    suspend fun configRetailPayment(cardSerialNumber: String): RetroApiResponse<ApiResponse>
    suspend fun configOnlineBanking(cardSerialNumber: String): RetroApiResponse<ApiResponse>
}

