package co.yap.networking.cards

import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.models.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardsRetroService {

    //    Create Card Pin
    @GET(CardsRepository.URL_CREATE_PIN)
    suspend fun createCardPin(@Path("card-serial-number") cardSerialNumber: String, @Body createCardPinRequest: CreateCardPinRequest): Response<ApiResponse>


    //    Order Card
    @POST(CardsRepository.URL_ORDER_CARD)
    suspend fun orderCard(@Body orderCardRequest: OrderCardRequest): Response<ApiResponse>
}