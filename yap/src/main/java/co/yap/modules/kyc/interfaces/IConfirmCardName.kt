package co.yap.modules.kyc.interfaces

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IConfirmCardName {
    interface State : IBase.State {
        var fullName: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(viewId: Int)
    }

    interface View : IBase.View<ViewModel>
}