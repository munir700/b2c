package co.yap.billpayments.paybills

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBills {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressView(id: Int)
    }

    interface View : IBase.View<ViewModel>{
        fun setObservers()
        fun removeObservers()
    }
}