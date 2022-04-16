package co.yap.networking.leanteach

import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.accountlistmodel.AccountListMainModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface LeanTechService {

    // Lean user on board
    @GET(LeanTechRepository.URL_ON_BOARD)
    suspend fun onBoardUser(): Response<BaseResponse<LeanOnBoardModel>>

    // Lean Bank List Request
    @GET(LeanTechRepository.URL_BANK_LIST)
    suspend fun bankList(): Response<BaseListResponse<BankListMainModel>>

    //Lean Account List Request
    @GET(LeanTechRepository.URL_ACCOUNT_LIST)
    suspend fun accountList(): Response<BaseListResponse<AccountListMainModel>>
}