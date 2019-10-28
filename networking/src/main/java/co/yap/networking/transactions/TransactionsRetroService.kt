package co.yap.networking.transactions

import co.yap.networking.models.ApiResponse
import co.yap.networking.transactions.requestdtos.AddEditNoteRequest
import co.yap.networking.transactions.requestdtos.AddFundsRequest
import co.yap.networking.transactions.requestdtos.RemoveFundsRequest
import co.yap.networking.transactions.requestdtos.Y2YFundsTransferRequest
import co.yap.networking.transactions.responsedtos.*
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
}