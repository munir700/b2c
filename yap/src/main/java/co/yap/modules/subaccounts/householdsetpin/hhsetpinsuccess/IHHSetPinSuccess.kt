package co.yap.modules.subaccounts.householdsetpin.hhsetpinsuccess

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSetPinSuccess {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleButtonPress(id: Int)
    }

    interface State : IBase.State
}