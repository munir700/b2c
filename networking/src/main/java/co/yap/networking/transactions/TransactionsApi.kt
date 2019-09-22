package co.yap.networking.transactions

import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.requestdtos.AddFundsRequest
import co.yap.networking.transactions.requestdtos.RemoveFundsRequest
import co.yap.networking.transactions.responsedtos.AddRemoveFundsResponse
import co.yap.networking.transactions.responsedtos.FundTransferDenominationsResponse
import co.yap.networking.transactions.responsedtos.FundTransferLimitsResponse

interface TransactionsApi {
    suspend fun addFunds(addFundsRequest: AddFundsRequest): RetroApiResponse<AddRemoveFundsResponse>
    suspend fun removeFunds(removeFundsResponse: RemoveFundsRequest): RetroApiResponse<AddRemoveFundsResponse>
    suspend fun getFundTransferLimits(productCode: String): RetroApiResponse<FundTransferLimitsResponse>
    suspend fun getFundTransferDenominations(productCode: String): RetroApiResponse<FundTransferDenominationsResponse>
}