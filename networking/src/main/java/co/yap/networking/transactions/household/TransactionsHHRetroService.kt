package co.yap.networking.transactions.household

import co.yap.networking.customers.household.responsedtos.HouseHoldLastNextSalary
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.transactions.household.TransactionsHHRepository.URL_GET_ALL_HOUSE_HOLD_PROFILE_TRANSACTIONS
import co.yap.networking.transactions.household.TransactionsHHRepository.URL_GET_HOUSE_HOLD_PROFILE_TRANSACTIONS
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import retrofit2.Response
import retrofit2.http.*

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

    // Get Account Transactions
    @GET(URL_GET_HOUSE_HOLD_PROFILE_TRANSACTIONS)
    suspend fun getHHTransactionsByPage(
        @Path("{householdAccountUUID}") accountUUID: String?,
        @Path("page_no") number: Int?,
        @Path("page_size") size: Int?,
        @Query("amountStartRange") minAmount: Double?,
        @Query("amountEndRange") maxAmount: Double?,
        @Query("txnType") txnType: String?,
        @Query("title") title: String?
    ): Response<HomeTransactionsResponse>

    // Get Account Transactions
    @GET(URL_GET_ALL_HOUSE_HOLD_PROFILE_TRANSACTIONS)
    suspend fun getAllHHProfileTransactions(
        @Path("householdAccountUUID") accountUUID: String?
    ): Response<BaseListResponse<Transaction>>
}