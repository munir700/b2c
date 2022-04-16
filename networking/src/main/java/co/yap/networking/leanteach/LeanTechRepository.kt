package co.yap.networking.leanteach

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.accountlistmodel.AccountListMainModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse

object LeanTechRepository : BaseRepository(), LeanTechApi {

    const val URL_ON_BOARD = "/lean-tech/api/onboard"
    const val URL_BANK_LIST = "/lean-tech/api/bank-list"
    const val URL_ACCOUNT_LIST = "/lean-tech/api/getPaymentSources"

    private val api: LeanTechService =
        RetroNetwork.createService(LeanTechService::class.java)

    override suspend fun onBoardUser(): RetroApiResponse<BaseResponse<LeanOnBoardModel>> =
        executeSafely(call = { api.onBoardUser() })

    override suspend fun bankList(): RetroApiResponse<BaseListResponse<BankListMainModel>> =
        executeSafely(call = { api.bankList() })

    override suspend fun accountList(): RetroApiResponse<BaseListResponse<AccountListMainModel>> =
        executeSafely(call = { api.accountList() })
}