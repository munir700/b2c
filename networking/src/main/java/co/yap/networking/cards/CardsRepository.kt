package co.yap.networking.cards

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object CardsRepository : BaseRepository(), CardsApi {

    const val URL_CREATE_PIN = "/cards/api/cards/create-pin/{card-serial-no}"
    private val API: CardsRetroService = RetroNetwork.createService(CardsRetroService::class.java)

    override suspend fun createCardPin(
        createCardPinRequest: CreateCardPinRequest,
        cardSerialNumber: String
    ): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.createCardPin(cardSerialNumber, createCardPinRequest) })
}
