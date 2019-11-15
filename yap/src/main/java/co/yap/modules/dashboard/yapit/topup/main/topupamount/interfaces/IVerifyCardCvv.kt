package co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IVerifyCardCvv {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun buttonClickEvent(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State
}