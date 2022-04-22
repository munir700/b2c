package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IKfsNotification {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleOnPressView(id: Int)

    }

    interface State : IBase.State {
        var valid: ObservableBoolean
    }
}