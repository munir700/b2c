package co.yap.household.onboard.onboarding.householdsuccess

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldSuccess {

    interface View: IBase.View<ViewModel> {

    }

    interface ViewModel: IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State: IBase.State {
        var name: String?
    }
}