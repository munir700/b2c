package co.yap.household.dashboard.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseholdHome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun requestTransactions(isLoadMore: Boolean = false)
        val transactionAdapter: ObservableField<HomeTransactionAdapter>?
        val notificationAdapter: ObservableField<HHNotificationAdapter>?
        val profilePictureAdapter: ObservableField<ProfilePictureAdapter>
    }

    interface State : IBase.State {
        val transactionList: ObservableField<MutableList<HomeTransactionListData>>
        var showNotification: MutableLiveData<Boolean>
        val transactionMap: MutableLiveData<Map<String?, List<Transaction>>>?
        var homeTransactionRequest: MutableLiveData<HomeTransactionsRequest>?
        var availableBalance: MutableLiveData<String>?
    }
}
