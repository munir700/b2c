package co.yap.networking.transactions.household

import co.yap.networking.models.ApiResponse
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionsHHRetroService {
    @POST(TransactionsHHRepository.URL_IBAN_SEND_MONEY)
    suspend fun ibanSendMoney(@Body request: IbanSendMoneyRequest?): Response<ApiResponse>
}