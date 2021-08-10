package co.yap.modules.kyc.interfaces

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IEditCardName {
    interface State : IBase.State {
        var fullName: ObservableField<String>
        var CardPrefix : ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handleOnPressView(id: Int)
        var clickEvent: SingleClickEvent
    }

    interface View : IBase.View<ViewModel>
}