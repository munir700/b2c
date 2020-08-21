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
    const val URL_IBAN_HOUSE_HOLD_GET_LAST_TRANSACTION =
        "transactions/api/household/get-last-transaction/{UUID}/{category}"
    const val URL_IBAN_HOUSE_HOLD_GET_LAST_NEXT_TRANSACTION =
        "transactions/api/household/last-next-transaction/{UUID}"

    override suspend fun ibanSendMoney(request: IbanSendMoneyRequest?) =
        CustomersHHRepository.executeSafely(call = { apiService.ibanSendMoney(request) })

    override suspend fun getLastTransaction(uuid: String?, category: String?) =
        CustomersHHRepository.executeSafely(call = {
            apiService.getLastTransaction(
                uuid,
                category
            )
        })

    override suspend fun getLastNextTransaction(uuid: String?) =
        CustomersHHRepository.executeSafely(call = {
            apiService.getLastNextTransaction(
                uuid
            )
        })
}
