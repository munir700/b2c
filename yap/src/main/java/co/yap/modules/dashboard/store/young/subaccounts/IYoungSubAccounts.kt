package co.yap.modules.dashboard.store.young.subaccounts

import co.yap.yapcore.IBase

class IYoungSubAccounts {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>
    {
        fun getSubAccount()
    }

    interface State : IBase.State
}