package co.yap.modules.dashboard.yapit.y2y.transfer.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IY2YTransfer {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {

    }
}