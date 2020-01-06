package co.yap.household.onboarding.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldCreatePassCode {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleClickEvent
        fun handlePressOnCreatePasscodeButton(id:Int)
    }

    interface State : IBase.State {
        var dialerError: String
        var buttonValidation: Boolean
    }
}