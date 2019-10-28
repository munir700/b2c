package co.yap.modules.dashboard.home.interfaces

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState

interface IYapHome {

    interface View : IBase.View<ViewModel> {
        var transactionViewHelper: TransactionsViewHelper?
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_SET_CARD_PIN: Int get() = 1
        val EVENT_SET_COMPLETE_VEERIFICATION: Int get() = 2
        var MAX_CLOSING_BALANCE: Double
        var debitCardSerialNumber: String
        val clickEvent: SingleClickEvent
        fun getDebitCards()
        val transactionLogicHelper: TransactionLogicHelper
        fun handlePressOnView(id: Int)

        val transactionsLiveData: LiveData<PagedList<HomeTransactionListData>>
        fun getState(): LiveData<PagingState>
        fun listIsEmpty(): Boolean
        fun retry()

    }

    interface State : IBase.State{
        var availableBalance : String
    }
}