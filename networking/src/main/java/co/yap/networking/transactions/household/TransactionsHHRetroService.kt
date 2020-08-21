package co.yap.networking.transactions.household

import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.responsedtos.HouseHoldLastNextSalary
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionsHHRetroService {
    @POST(TransactionsHHRepository.URL_IBAN_SEND_MONEY)
    suspend fun ibanSendMoney(@Body request: IbanSendMoneyRequest?): Response<ApiResponse>

    @GET(TransactionsHHRepository.URL_IBAN_HOUSE_HOLD_GET_LAST_TRANSACTION)
    suspend fun getLastTransaction(
        @Path("UUID") uuid: String?,
        @Path("category") category: String?
    ): Response<BaseResponse<SalaryTransaction>>

    @GET(TransactionsHHRepository.URL_IBAN_HOUSE_HOLD_GET_LAST_NEXT_TRANSACTION)
    suspend fun getLastNextTransaction(@Path("UUID") uuid: String?): Response<BaseListResponse<HouseHoldLastNextSalary>>
}