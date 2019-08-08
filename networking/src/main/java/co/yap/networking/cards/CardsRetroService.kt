package co.yap.networking.cards

import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.responsedtos.GetCardsResponse
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface CardsRetroService {

    // Create Card Pin
    @POST(CardsRepository.URL_CREATE_PIN)
    suspend fun createCardPin(@Path("card-serial-number") cardSerialNumber : String, @Body createCardPinRequest: CreateCardPinRequest): Response<ApiResponse>

    // Get Cards
    @GET(CardsRepository.URL_GET_CARDS)
    suspend fun getDebitCards(@Query("cardType") cardType : String): Response<GetCardsResponse>

}