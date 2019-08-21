package co.yap.networking.cards

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.responsedtos.GetCardsResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object CardsRepository : BaseRepository(), CardsApi {
    const val URL_CREATE_PIN = "/cards/api/cards/create-pin/{card-serial-number}"
    const val URL_GET_CARDS = "/cards/api/cards"
    const val URL_ORDER_CARD = "/cards/api/cards/b2c/physical"
    private val API: CardsRetroService = RetroNetwork.createService(CardsRetroService::class.java)

    override suspend fun createCardPin(
        createCardPinRequest: CreateCardPinRequest,
        cardSerialNumber: String
    ): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.createCardPin(cardSerialNumber, createCardPinRequest) })

    override suspend fun getDebitCards(cardType: String): RetroApiResponse<GetCardsResponse> =
        AuthRepository.executeSafely(call = { API.getDebitCards(cardType) })


    override suspend fun orderCard(
        orderCardRequest: OrderCardRequest
    ): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.orderCard(orderCardRequest) })

}
