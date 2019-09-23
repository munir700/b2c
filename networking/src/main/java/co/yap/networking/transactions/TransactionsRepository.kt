package co.yap.networking.transactions

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.requestdtos.AddFundsRequest
import co.yap.networking.transactions.responsedtos.AddRemoveFundsResponse
import co.yap.networking.transactions.requestdtos.RemoveFundsRequest
import co.yap.networking.transactions.responsedtos.CardFeeResponse
import co.yap.networking.transactions.responsedtos.FundTransferDenominationsResponse
import co.yap.networking.transactions.responsedtos.FundTransferLimitsResponse

object TransactionsRepository : BaseRepository(), TransactionsApi {

    const val URL_ADD_FUNDS = "/transactions/api/top-up"
    const val URL_REMOVE_FUNDS = "/transactions/api/withdraw"
    const val URL_FUND_TRANSFER_LIMITS = "/transactions/api/product/{product-code}/limits"
    const val URL_FUND_TRANSFER_DENOMINATIONS = "/transactions/api/product/{product-code}/denominations"
    const val URL_GET_CARD_FEE = "/transactions/api/fees/spare-card/subscription/{card-type}"

    private val api: TransactionsRetroService =
        RetroNetwork.createService(TransactionsRetroService::class.java)

    override suspend fun addFunds(addFundsRequest: AddFundsRequest): RetroApiResponse<AddRemoveFundsResponse> =
        executeSafely(call = { api.addFunds(addFundsRequest) })

    override suspend fun removeFunds(removeFundsResponse: RemoveFundsRequest): RetroApiResponse<AddRemoveFundsResponse> =
        executeSafely(call = { api.removeFunds(removeFundsResponse) })

    override suspend fun getFundTransferLimits(productCode: String): RetroApiResponse<FundTransferLimitsResponse> =
        executeSafely(call = { api.getFundTransferLimits(productCode) })

    override suspend fun getFundTransferDenominations(productCode: String): RetroApiResponse<FundTransferDenominationsResponse> =
        executeSafely(call = { api.getFundTransferDenominations(productCode) })

    override suspend fun getCardFee(cardType: String): RetroApiResponse<CardFeeResponse> =
        executeSafely(call = { api.getCardFee(cardType)})

}
