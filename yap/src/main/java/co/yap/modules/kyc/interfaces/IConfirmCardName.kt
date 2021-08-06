package co.yap.modules.kyc.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IConfirmCardName {
    interface State : IBase.State {
        var fullName: String
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(viewId: Int)
    }

    interface View : IBase.View<ViewModel>
}