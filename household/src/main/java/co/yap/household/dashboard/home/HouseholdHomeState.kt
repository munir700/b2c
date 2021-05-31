package co.yap.household.dashboard.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.requestdtos.REQUEST_PAGE_SIZE
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.State
import co.yap.yapcore.BaseState

class HouseholdHomeState : BaseState(), IHouseholdHome.State {
    override var showNotification: MutableLiveData<Boolean> = MutableLiveData(true)
    override val transactionList: ObservableField<MutableList<HomeTransactionListData>> =
        ObservableField(mutableListOf())
    override val transactionMap: MutableLiveData<Map<String?, List<Transaction>>>? =
        MutableLiveData()
    override var homeTransactionRequest: MutableLiveData<HomeTransactionsRequest>? =
        MutableLiveData()
    override var availableBalance: MutableLiveData<String>? = MutableLiveData("")
    override var accountActivateLiveData: MutableLiveData<State>? = MutableLiveData()
    override var card: MutableLiveData<Card>? = MutableLiveData()
    override var transactionRequest: HomeTransactionsRequest? =
        HomeTransactionsRequest(size = REQUEST_PAGE_SIZE,
            amountStartRange = null,
            amountEndRange = null)
    override var address: MutableLiveData<Address>? = MutableLiveData()
}