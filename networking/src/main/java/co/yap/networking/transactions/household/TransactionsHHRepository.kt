package co.yap.networking.transactions.household

import co.yap.networking.BaseRepository
import co.yap.networking.RetroNetwork
import co.yap.networking.transactions.TransactionsApi
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest

object TransactionsHHRepository : BaseRepository(), TransactionsApi by TransactionsRepository,
    TransactionsHHApi {
    private var apiService: TransactionsHHRetroService =
        RetroNetwork.createService(TransactionsHHRetroService::class.java)

    const val URL_IBAN_SEND_MONEY = "/transactions/api/y2y-household/"
    const val URL_IBAN_HOUSE_HOLD_GET_LAST_TRANSACTION =
        "transactions/api/get-last-transaction/{UUID}/{category}"
    const val URL_IBAN_HOUSE_HOLD_GET_LAST_NEXT_TRANSACTION =
        "transactions/api/last-next-transaction/{UUID}"

    const val URL_GET_ALL_HOUSE_HOLD_PROFILE_TRANSACTIONS =
        "/transactions/api/get-all-transactions/{householdAccountUUID}/TRANSACTION"
    const val URL_GET_HOUSE_HOLD_PROFILE_TRANSACTIONS =
        "/transactions/api/household-account-transactions"

    override suspend fun ibanSendMoney(request: IbanSendMoneyRequest?) =
        executeSafely(call = { apiService.ibanSendMoney(request) })

    override suspend fun getLastTransaction(uuid: String?, category: String?) =
        executeSafely(call = {
            apiService.getLastTransaction(
                uuid,
                category
            )
        })

    override suspend fun getLastNextTransaction(uuid: String?) =
        executeSafely(call = {
            apiService.getLastNextTransaction(
                uuid
            )
        })

    override suspend fun getHHTransactionsByPage(
        request: HomeTransactionsRequest
    ) = executeSafely(call = {
        apiService.getHHTransactionsByPage(request
        )
    })

    override suspend fun getAllHHProfileTransactions(accountUUID: String?) = executeSafely(call = {
        apiService.getAllHHProfileTransactions(accountUUID)
    })
}
