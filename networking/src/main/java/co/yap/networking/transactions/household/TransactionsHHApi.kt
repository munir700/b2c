package co.yap.networking.transactions.household

import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsApi
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest

interface TransactionsHHApi: TransactionsApi {
    suspend fun ibanSendMoney(request: IbanSendMoneyRequest?): RetroApiResponse<ApiResponse>
}