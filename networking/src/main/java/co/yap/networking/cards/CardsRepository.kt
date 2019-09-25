package co.yap.networking.cards

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.cards.requestdtos.*
import co.yap.networking.cards.responsedtos.CardBalanceResponseDTO
import co.yap.networking.cards.responsedtos.CardDetailResponseDTO
import co.yap.networking.cards.responsedtos.GetCardsResponse
import co.yap.networking.cards.responsedtos.GetPhysicalAddress
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
    const val URL_ADD_SPARE_VIRTUAL_CARD = "/cards/api/cards/supplementary/virtual"
    const val URL_ADD_SPARE_PHYSICAL_CARD = "/cards/api/cards/supplementary"
    const val URL_GET_PHYSICAL_CARD_ADDRESS = "/cards/api/user-address"

    const val URL_GET_CARD_BALANCE = "/cards/api/cards/balance"
    const val URL_CARD_FREEZE_UNFREEZE = "/cards/api/cards/block-unblock"
    const val URL_GET_CARD_DETAILS = "/cards/api/cards/details"
    const val URL_REMOVE_CARD = "/cards/api/cards/close"
    const val URL_UPDATE_CARD_NAME = "/cards/api/cards/card-name"

    const val URL_REPORT_LOST_OR_STOLEN_CARD = "/cards/api/card-hot-list"

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

    override suspend fun getAccountBalanceRequest(): RetroApiResponse<CardBalanceResponseDTO> =
        AuthRepository.executeSafely(call = { API.getAccountBalanceRequest() })


    override suspend fun configAllowAtm(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.configAllowAtm(cardLimitConfigRequest) })

    override suspend fun configAbroadPayment(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.configAbroadPayment(cardLimitConfigRequest) })

    override suspend fun configRetailPayment(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.configRetailPayment(cardLimitConfigRequest) })

    override suspend fun configOnlineBanking(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.configOnlineBanking(cardLimitConfigRequest) })

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


    override suspend fun getUserAddressRequest(): RetroApiResponse<GetPhysicalAddress> =
        AuthRepository.executeSafely(call = { API.getPhysicalCardAddress() })


    override suspend fun getCardBalance(cardSerialNumber: String): RetroApiResponse<CardBalanceResponseDTO> =
        AuthRepository.executeSafely(call = { API.getCardBalance(cardSerialNumber) })

    override suspend fun freezeUnfreezeCard(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.freezeUnfreezeCard(cardLimitConfigRequest) })

    override suspend fun getCardDetails(cardSerialNumber: String): RetroApiResponse<CardDetailResponseDTO> =
        AuthRepository.executeSafely(call = { API.getCardDetails(cardSerialNumber) })

    override suspend fun removeCard(cardLimitConfigRequest: CardLimitConfigRequest): RetroApiResponse<ApiResponse> =
        AuthRepository.executeSafely(call = { API.removeCard(cardLimitConfigRequest) })

    override suspend fun updateCardName(
        cardName: String,
        cardSerialNumber: String
    ): RetroApiResponse<CardDetailResponseDTO> =
        AuthRepository.executeSafely(call = { API.updateCardName(cardName, cardSerialNumber) })

    override suspend fun reportAndBlockCard(cardsHotlistReequest: CardsHotlistRequest): RetroApiResponse<ApiResponse> =

        AuthRepository.executeSafely(call = {
            API.reportAndBlockCard(
                cardsHotlistReequest
            )
        })
}
