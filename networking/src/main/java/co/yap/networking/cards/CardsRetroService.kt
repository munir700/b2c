package co.yap.networking.cards

import co.yap.networking.cards.requestdtos.ConfigAtm
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

    @PUT(CardsRepository.URL_ALLOW_ATM)
    suspend fun configAllowAtm(@Body configAtm: ConfigAtm): Response<ApiResponse>

    @PUT(CardsRepository.URL_ABROAD_PAYMENT)
    suspend fun configAbroadPayment(@Query("cardSerialNumber") cardSerialNumber: String): Response<ApiResponse>

    @PUT(CardsRepository.URL_ONLINE_BANKING)
    suspend fun configOnlineBanking(@Query("cardSerialNumber") cardSerialNumber: String): Response<ApiResponse>

    @PUT(CardsRepository.URL_RETAIL_PAYMENT)
    suspend fun configRetailPayment(@Query("cardSerialNumber") cardSerialNumber: String): Response<ApiResponse>

}