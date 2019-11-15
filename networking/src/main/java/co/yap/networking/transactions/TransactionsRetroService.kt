package co.yap.networking.transactions

import co.yap.networking.models.ApiResponse
import co.yap.networking.transactions.requestdtos.*
import co.yap.networking.transactions.responsedtos.*
import co.yap.networking.transactions.responsedtos.topuptransactionsession.CreateTransactionSessionResponseDTO
import co.yap.networking.transactions.responsedtos.topuptransactionsession.Check3DEnrollmentSessionResponse
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import retrofit2.Response
import retrofit2.http.*

interface TransactionsRetroService {

    // Add funds
    @POST(TransactionsRepository.URL_ADD_FUNDS)
    suspend fun addFunds(@Body addFundsRequest: AddFundsRequest): Response<AddRemoveFundsResponse>

    // Remove funds
    @POST(TransactionsRepository.URL_REMOVE_FUNDS)
    suspend fun removeFunds(@Body removeFundsResponse: RemoveFundsRequest): Response<AddRemoveFundsResponse>

    // Get fund transfer limits
    @GET(TransactionsRepository.URL_FUND_TRANSFER_LIMITS)
    suspend fun getFundTransferLimits(@Path("product-code") productCode: String): Response<FundTransferLimitsResponse>

    // Get fund transfer denominations
    @GET(TransactionsRepository.URL_FUND_TRANSFER_DENOMINATIONS)
    suspend fun getFundTransferDenominations(@Path("product-code") productCode: String): Response<FundTransferDenominationsResponse>

    // Get fund transfer denominations
    @GET(TransactionsRepository.URL_GET_CARD_FEE)
    suspend fun getCardFee(@Path("card-type") cardType: String): Response<CardFeeResponse>

    // Get Card Statements
    @GET(TransactionsRepository.URL_GET_CARD_STATEMENTS)
    suspend fun getCardStatements(@Query("cardSerialNumber") cardSerialNumber: String): Response<CardStatementsResponse>

    // Get Card Statements
    @POST(TransactionsRepository.URL_Y2Y_FUNDS_TRANSFER)
    suspend fun y2yFundsTransferRequest(@Body y2YFundsTransferRequest: Y2YFundsTransferRequest): Response<ApiResponse>

    // AddEdit Note
    @POST(TransactionsRepository.URL_ADD_EDIT_NOTE)
    suspend fun addEditNote(@Body addEditNoteRequest: AddEditNoteRequest): Response<AddEditNoteResponse>

    // Dashboard filter Amount
    @GET(TransactionsRepository.URL_SEARCH_FILTER_AMOUNT)
    suspend fun getSearchFilterAmount(): Response<SearchFilterAmountResponse>

    // Transaction details
    @GET(TransactionsRepository.URL_GET_TRANSACTION_DETAILS)
    suspend fun getTransactionDetails(@Path("transactionId") transactionId: String): Response<TransactionDetailsResponse>

    // Get Account Transaction
    @GET(TransactionsRepository.URL_GET_ACCOUNT_TRANSACTIONS)
    suspend fun getAccountTransactions(
        @Path("number") number: Int,
        @Path("size") size: Int,
        @Query("amountStartRange") minAmount: Double?,
        @Query("amountEndRange") maxAmount: Double?,
        @Query("txnType") txnType: String?,
        @Query("title") title: String?
    ): Response<HomeTransactionsResponse>

    // Get Account Transaction
    @GET(TransactionsRepository.URL_GET_CARD_TRANSACTIONS)
    suspend fun getCardTransactions(
        @Path("number") number: Int,
        @Path("size") size: Int,
        @Query("cardSerialNumber") cardSerialNumber: String
    ): Response<HomeTransactionsResponse>

    // Get transaction fee
    @GET(TransactionsRepository.URL_GET_FEE)
    suspend fun getTransactionFee(@Query("type") type: String): Response<TransactionFeeResponseDTO>

    // Create transaction session
    @POST(TransactionsRepository.URL_CREATE_TRANSACTION_SESSION)
    suspend fun createTransactionSession(@Body createSessionRequest: CreateSessionRequest): Response<CreateTransactionSessionResponseDTO>
   // Check 3ds enrollment session
    @PUT(TransactionsRepository.URL_CHECK_3Ds_ENROLLMENT_SESSION)
    suspend fun check3DEnrollmentSession(@Body check3DEnrollmentSessionRequest: Check3DEnrollmentSessionRequest): Response<Check3DEnrollmentSessionResponse>

    // Secure id pooling
    @GET(TransactionsRepository.URL_SECURE_ID_POOLING)
    suspend fun secureIdPooling(@Query("3DSecureId") secureId: String): Response<StringDataResponseDTO>
}