package co.yap.networking.leanteach

import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.RetroApiResponse

interface LeanTechApi {
    suspend fun bankList(): RetroApiResponse<BaseListResponse<BankListMainModel>>
}