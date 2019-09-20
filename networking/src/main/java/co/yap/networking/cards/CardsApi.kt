package co.yap.networking.cards

import co.yap.networking.cards.requestdtos.AddPhysicalSpareCardRequest
import co.yap.networking.cards.requestdtos.AddVirtualSpareCardRequest
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.responsedtos.CardBalanceResponseDTO
import co.yap.networking.cards.responsedtos.CardDetailResponseDTO
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

    suspend fun getAccountBalanceRequest(): RetroApiResponse<CardBalanceResponseDTO>
    suspend fun configAllowAtm(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse>
    suspend fun configAbroadPayment(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse>
    suspend fun configRetailPayment(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse>
    suspend fun configOnlineBanking(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse>
    suspend fun addSpareVirtualCard(
        addVirtualSpareCardRequest: AddVirtualSpareCardRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun addSparePhysicalCard(
        addPhysicalSpareCardRequest: AddPhysicalSpareCardRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun getUserAddressRequest(): RetroApiResponse<ApiResponse>
    suspend fun getCardBalance(cardSerialNumber: String): RetroApiResponse<CardBalanceResponseDTO>
    suspend fun freezeUnfreezeCard(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse>
    suspend fun getCardDetails(cardSerialNumber: String): RetroApiResponse<CardDetailResponseDTO>
    suspend fun removeCard(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse>
    suspend fun updateCardName(cardName: String,cardSerialNumber: String): RetroApiResponse<CardDetailResponseDTO>
}