package co.yap.modules.kyc.amendments.passportactivity

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IPassport {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIcon: ObservableBoolean
        var leftIcon: ObservableBoolean
        var totalProgress: Int
        var currentProgress: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface View : IBase.View<ViewModel> {
    }
}