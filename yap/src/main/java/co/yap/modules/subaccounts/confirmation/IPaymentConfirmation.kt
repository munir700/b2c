package co.yap.modules.subaccounts.confirmation

import co.yap.yapcore.IBase

interface IPaymentConfirmation {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}