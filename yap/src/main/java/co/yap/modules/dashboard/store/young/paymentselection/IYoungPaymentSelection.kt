package co.yap.modules.dashboard.store.young.paymentselection

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IYoungPaymentSelection {
    interface State : IBase.State {

    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface View : IBase.View<ViewModel>
}