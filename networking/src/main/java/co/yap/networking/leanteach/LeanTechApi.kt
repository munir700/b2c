package co.yap.networking.leanteach

import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.accountlistmodel.AccountListMainModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse

interface LeanTechApi {
    suspend fun onBoardUser(): RetroApiResponse<BaseResponse<LeanOnBoardModel>>
    suspend fun bankList(): RetroApiResponse<BaseListResponse<BankListMainModel>>
    suspend fun accountList(): RetroApiResponse<BaseListResponse<AccountListMainModel>>
}