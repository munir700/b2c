package co.yap.networking.transactions.household

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.transactions.TransactionsApi
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest

object TransactionsHHRepository : BaseRepository(), TransactionsApi by TransactionsRepository,
    TransactionsHHApi {
    private var apiService: TransactionsHHRetroService =
        RetroNetwork.createService(TransactionsHHRetroService::class.java)

    const val URL_IBAN_SEND_MONEY = "/transactions/api/y2y-household/"

    override suspend fun ibanSendMoney(request: IbanSendMoneyRequest?) =
        CustomersHHRepository.executeSafely(call = { apiService.ibanSendMoney(request) })
}
