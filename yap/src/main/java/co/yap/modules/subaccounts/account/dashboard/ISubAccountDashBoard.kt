package co.yap.modules.subaccounts.account.dashboard

import co.yap.yapcore.IBase

interface ISubAccountDashBoard {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}