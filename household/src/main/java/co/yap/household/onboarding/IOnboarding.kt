package co.yap.household.onboarding

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IOnboarding {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        var myName: String
    }
}