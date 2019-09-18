package co.yap.networking.cards

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.cards.requestdtos.AddPhysicalSpareCardRequest
import co.yap.networking.cards.requestdtos.AddVirtualSpareCardRequest
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.responsedtos.DebitCardBalanceResponseDTO
import co.yap.networking.cards.responsedtos.GetCardsResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object CardsRepository : BaseRepository(), CardsApi {
    override suspend fun getUserAddressRequest(): RetroApiResponse<ApiResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    const val URL_CREATE_PIN = "/cards/api/cards/create-pin/{card-serial-number}"
    const val URL_GET_CARDS = "/cards/api/cards"
    const val URL_ORDER_CARD = "/cards/api/cards/b2c/physical"
    const val URL_GET_DEBIT_CARD_BALANCE = "cards/api/cards/debit/balance"
    const val URL_ADD_SPARE_VIRTUAL_CARD =
        "https://dev.yap.co/cards/api/cards/supplementary/virtual"
    const val URL_ADD_SPARE_PHYSICAL_CARD = "https://dev.yap.co/cards/api/cards/supplementary"
    const val URL_GET_PHYSICAL_CARD_ADDRESS = "https://dev.yap.co/cards/api/user-address"

    private val API: CardsRetroService = RetroNetwork.createService(CardsRetroService::class.java)

    override suspend fun createCardPin(
        createCardPinRequest: CreateCardPinRequest,
        cardSerialNumber: String
    ): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = {
            API.createCardPin(
                cardSerialNumber,
                createCardPinRequest
            )
        })

    override suspend fun getDebitCards(cardType: String): RetroApiResponse<GetCardsResponse> =
        AuthRepository.executeSafely(call = { API.getDebitCards(cardType) })


    override suspend fun orderCard(
        orderCardRequest: OrderCardRequest
    ): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.orderCard(orderCardRequest) })

    override suspend fun getAccountBalanceRequest(): RetroApiResponse<DebitCardBalanceResponseDTO> =
        AuthRepository.executeSafely(call = { API.getAccountBalanceRequest() })

    override suspend fun addSpareVirtualCard(
        addVirtualSpareCardRequest: AddVirtualSpareCardRequest
    ): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = {
            API.addSpareVirtualCardRequest(
                addVirtualSpareCardRequest
            )
        })

    override suspend fun addSparePhysicalCard(addPhysicalSpareCardRequest: AddPhysicalSpareCardRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = {
            API.addSparePhysicalCardRequest(
                addPhysicalSpareCardRequest
            )
        })


}
