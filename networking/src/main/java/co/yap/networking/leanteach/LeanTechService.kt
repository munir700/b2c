package co.yap.networking.leanteach

import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.BaseListResponse
import retrofit2.Response
import retrofit2.http.GET

interface LeanTechService {

    // Lean Bank Request
    @GET(LeanTechRepository.URL_BANK_LIST)
    suspend fun bankList(): Response<BaseListResponse<BankListMainModel>>
}