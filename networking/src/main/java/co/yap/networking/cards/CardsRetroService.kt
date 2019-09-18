package co.yap.networking.cards

import co.yap.networking.cards.requestdtos.AddPhysicalSpareCardRequest
import co.yap.networking.cards.requestdtos.AddVirtualSpareCardRequest
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.responsedtos.DebitCardBalanceResponseDTO
import co.yap.networking.cards.responsedtos.GetCardsResponse
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface CardsRetroService {

    // Create Card Pin
    @POST(CardsRepository.URL_CREATE_PIN)
    suspend fun createCardPin(@Path("card-serial-number") cardSerialNumber: String, @Body createCardPinRequest: CreateCardPinRequest): Response<ApiResponse>

    // Get Cards
    @GET(CardsRepository.URL_GET_CARDS)
    suspend fun getDebitCards(@Query("cardType") cardType: String): Response<GetCardsResponse>

    //    Order Card
    @POST(CardsRepository.URL_ORDER_CARD)
    suspend fun orderCard(@Body orderCardRequest: OrderCardRequest): Response<ApiResponse>

    // get card balance
    @GET(CardsRepository.URL_GET_DEBIT_CARD_BALANCE)
    suspend fun getAccountBalanceRequest(): Response<DebitCardBalanceResponseDTO>

    // add spare virtual card
    @POST(CardsRepository.URL_ADD_SPARE_VIRTUAL_CARD)
    suspend fun addSpareVirtualCardRequest(@Body addVirtualSpareCardRequest: AddVirtualSpareCardRequest): Response<ApiResponse>


    // add spare physical card
    @POST(CardsRepository.URL_ADD_SPARE_PHYSICAL_CARD)
    suspend fun addSparePhysicalCardRequest(@Body addPhysicalSpareCardRequest: AddPhysicalSpareCardRequest): Response<ApiResponse>

}