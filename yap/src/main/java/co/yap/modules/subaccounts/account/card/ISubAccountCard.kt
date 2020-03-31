package co.yap.modules.subaccounts.account.card

import co.yap.yapcore.IBase

interface ISubAccountCard {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}