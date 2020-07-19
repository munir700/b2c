package co.yap.household.onboarding.existingsuccess

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHOnBoardingExistingSuccess {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
    }

    interface State : IBase.State {
    }
}