package co.yap.modules.dashboard.store.young.confirmation

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungPaymentConfirmation {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
    }
}
