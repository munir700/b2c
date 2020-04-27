package co.yap.modules.subaccounts.paysalary.transfer.confirmation

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHIbanSendMoneyConfirmation {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
    }

    interface State : IBase.State
}