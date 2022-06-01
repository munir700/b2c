package co.yap.networking.transactions.household

import co.yap.networking.customers.household.responsedtos.HouseHoldLastNextSalary
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsApi
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionsResponse
import co.yap.networking.transactions.responsedtos.transaction.Transaction

interface TransactionsHHApi : TransactionsApi {
    suspend fun ibanSendMoney(request: IbanSendMoneyRequest?): RetroApiResponse<ApiResponse>

    /**
     * @param uuid the sub account user UUID.
     * @param category would be Salary/Expense
     * @return response of last Schedule Salary or Expense in Collection
     */
    suspend fun getLastTransaction(
        uuid: String?,
        category: String?
    ): RetroApiResponse<BaseResponse<SalaryTransaction>>

    suspend fun getLastNextTransaction(uuid: String?): RetroApiResponse<BaseListResponse<HouseHoldLastNextSalary>>

    suspend fun getHHTransactionsByPage(accountUUID: String?,homeTransactionsRequest: HomeTransactionsRequest?): RetroApiResponse<HomeTransactionsResponse>
    suspend fun getAllHHProfileTransactions(accountUUID: String?): RetroApiResponse<BaseListResponse<Transaction>>
}