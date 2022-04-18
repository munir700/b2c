package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IAccountList {
    interface View : IBase.View<ViewModel> {
        fun setObserver()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var accountList: MutableLiveData<MutableList<Any>>
        fun getAccountList()
    }

    interface State : IBase.State {}
}