package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import androidx.lifecycle.MutableLiveData
import co.yap.widgets.MultiStateView
import co.yap.yapcore.BaseState

class AccountListState : BaseState(),
    IAccountList.State {
    override var stateView: MutableLiveData<MultiStateView.ViewState> =
        MutableLiveData(MultiStateView.ViewState.CONTENT)
}