package co.yap.modules.dashboard.home.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapHome {

    interface View : IBase.View<ViewModel> {
        var transactionViewHelper: TransactionsViewHelper?
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var homeTransactionsRequest: HomeTransactionsRequest
        val EVENT_SET_CARD_PIN: Int get() = 1
        val EVENT_SET_COMPLETE_VEERIFICATION: Int get() = 2
        var MAX_CLOSING_BALANCE: Double
        var debitCardSerialNumber: String
        val clickEvent: SingleClickEvent
        fun getDebitCards()
        fun handlePressOnView(id: Int)
        val transactionsLiveData: MutableLiveData<List<HomeTransactionListData>>
        var isLoadMore: MutableLiveData<Boolean>
        fun loadMore()
        fun filterTransactions()
        fun requestAccountTransactions()

    }

    interface State : IBase.State {
        var availableBalance: String
        var filterCount: ObservableField<Int>
    }
}