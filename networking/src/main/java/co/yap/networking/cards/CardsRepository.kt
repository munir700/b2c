package co.yap.networking.cards

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.cards.requestdtos.ConfigAtm
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.responsedtos.DebitCardBalanceResponseDTO
import co.yap.networking.cards.responsedtos.GetCardsResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse

object CardsRepository : BaseRepository(), CardsApi {

    const val URL_CREATE_PIN = "/cards/api/cards/create-pin/{card-serial-number}"
    const val URL_GET_CARDS = "/cards/api/cards"
    const val URL_ORDER_CARD = "/cards/api/cards/b2c/physical"
    const val URL_GET_DEBIT_CARD_BALANCE = "/cards/api/cards/debit/balance"
    const val URL_ALLOW_ATM = "/cards/api/cards/atm-allow"
    const val URL_ONLINE_BANKING = "/cards/api/cards/online-banking"
    const val URL_ABROAD_PAYMENT = "/cards/api/cards/payment-abroad"
    const val URL_RETAIL_PAYMENT = "/cards/api/cards/retail-payment"

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


    override suspend fun configAllowAtm(configAtm: ConfigAtm): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.configAllowAtm(configAtm) })

    override suspend fun configAbroadPayment(cardSerialNumber: String): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.configAbroadPayment(cardSerialNumber) })

    override suspend fun configRetailPayment(cardSerialNumber: String): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.configRetailPayment(cardSerialNumber) })

    override suspend fun configOnlineBanking(cardSerialNumber: String): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.configOnlineBanking(cardSerialNumber) })

}
