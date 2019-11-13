package co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITopUpCardSuccess {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun buttonClickEvent(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        var buttonTitle: String
    }
}