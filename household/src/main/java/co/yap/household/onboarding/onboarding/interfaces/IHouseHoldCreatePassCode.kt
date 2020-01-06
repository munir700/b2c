package co.yap.household.onboarding.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldCreatePassCode {
    interface View : IBase.View<ViewModel>
    {
        fun setObservers()
    }
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent?
        fun handlePressOnCreatePasscodeButton(id:Int)
    }

    interface State : IBase.State {
        var dialerError: String
        var buttonValidation: Boolean
    }
}