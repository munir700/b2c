package co.yap.household.dashboard.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.State
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseholdHome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnView(id: Int)
        fun getPrimaryCard()
        fun getPaginationListener(): PaginatedRecyclerView.Pagination?
        var clickEvent: SingleClickEvent
        fun requestTransactions(
            transactionRequest: HomeTransactionsRequest?,
            isLoadMore: Boolean = false,
            apiResponse: ((co.yap.widgets.State) -> Unit?)? =null
        )

        val transactionAdapter: ObservableField<HomeTransactionAdapter>?
        val notificationAdapter: ObservableField<HHNotificationAdapter>?
    }

    interface State : IBase.State {
        val transactionList: ObservableField<MutableList<HomeTransactionListData>>
        var showNotification: MutableLiveData<Boolean>
        val transactionMap: MutableLiveData<Map<String?, List<Transaction>>>?
        var homeTransactionRequest: MutableLiveData<HomeTransactionsRequest>?
        var availableBalance: MutableLiveData<String>?
        var accountActivateLiveData: MutableLiveData<co.yap.widgets.State>?
        var card: MutableLiveData<Card>?
        var transactionRequest: HomeTransactionsRequest?
    }
}
