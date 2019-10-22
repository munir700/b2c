package co.yap.networking.transactions

import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.requestdtos.AddFundsRequest
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.requestdtos.RemoveFundsRequest
import co.yap.networking.transactions.responsedtos.*
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse

interface TransactionsApi {
    suspend fun addFunds(addFundsRequest: AddFundsRequest): RetroApiResponse<AddRemoveFundsResponse>
    suspend fun removeFunds(removeFundsResponse: RemoveFundsRequest): RetroApiResponse<AddRemoveFundsResponse>
    suspend fun getFundTransferLimits(productCode: String): RetroApiResponse<FundTransferLimitsResponse>
    suspend fun getFundTransferDenominations(productCode: String): RetroApiResponse<FundTransferDenominationsResponse>
    suspend fun getCardFee(cardType: String): RetroApiResponse<CardFeeResponse>
    suspend fun getCardStatements(cardSerialNumber: String): RetroApiResponse<CardStatementsResponse>
    suspend fun getAccountTransactions(homeTransactionsRequest: HomeTransactionsRequest): RetroApiResponse<HomeTransactionsResponse>
}