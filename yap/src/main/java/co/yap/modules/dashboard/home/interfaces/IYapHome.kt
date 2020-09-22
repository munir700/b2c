package co.yap.modules.dashboard.home.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.notification.HomeNotification
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapHome {

    interface View : IBase.View<ViewModel> {
        var transactionViewHelper: TransactionsViewHelper?
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_SET_CARD_PIN: Int get() = 1
        val EVENT_SET_COMPLETE_VEERIFICATION: Int get() = 2
        val ON_ADD_NEW_ADDRESS_EVENT: Int get() = 3
        var MAX_CLOSING_BALANCE: Double
        val clickEvent: SingleClickEvent
        var txnFilters: TransactionFilters
        fun getDebitCards()
        fun handlePressOnView(id: Int)
        val transactionsLiveData: MutableLiveData<List<HomeTransactionListData>>
        var isLoadMore: MutableLiveData<Boolean>
        var isRefreshing: MutableLiveData<Boolean>
        var isLast: MutableLiveData<Boolean>
        fun loadMore()
        fun filterTransactions()
        fun requestAccountTransactions()
        fun getNotifications(
            accountInfo: AccountInfo,
            paymentCard: Card,apiResponse: (( Boolean) -> Unit?)?
        )
        fun getFailedTransactionAndSubNotifications(apiResponse: (( Boolean) -> Unit?)?)
    }

    interface State : IBase.State {
        var availableBalance: String
        var filterCount: ObservableField<Int>
        var isTransEmpty:ObservableField<Boolean>
        var notificationList:MutableLiveData<MutableList<HomeNotification>>
    }
}