package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import androidx.lifecycle.MutableLiveData
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.widgets.MultiStateView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAccountList {
    interface View : IBase.View<ViewModel> {
        fun setObserver()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var accountList: MutableLiveData<MutableList<Any>>
        var accountListAdapter: AccountListAdapter
        val clickEvent: SingleClickEvent
        val leanOnBoardModel: MutableLiveData<LeanOnBoardModel>
        var customerId : String?
        fun getAccountList()
        fun handlePressOnView(id: Int)
        fun onboardUser()
        fun setMultiState()
    }

    interface State : IBase.State {
        var stateView: MutableLiveData<MultiStateView.ViewState>
    }
}