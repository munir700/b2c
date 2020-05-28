package co.yap.household.dashboard.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseholdHome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val isLoadMore: MutableLiveData<Boolean>
        val isLast: MutableLiveData<Boolean>
        var homeTransactionRequest: HomeTransactionsRequest
        val transactionsLiveData: MutableLiveData<List<HomeTransactionListData>>
        var adapter: ObservableField<HHNotificationAdapter>
        var MAX_CLOSING_BALANCE: Double
        fun handlePressOnView(id: Int)
        fun requestTransactions(isLoadMore: Boolean = false)
        fun loadMore()
    }

    interface State : IBase.State {
        val transactionList: ObservableField<MutableList<HomeTransactionListData>>
        var showNotification: MutableLiveData<Boolean>
    }
}
