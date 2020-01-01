package co.yap.modules.dashboard.store.household.onboarding.interfaces

import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IBaseOnboarding {

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        var username: String
        var userMobileNo: String
        var selectedPlanType: HouseHoldPlan
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        val backButtonPressEvent: SingleLiveEvent<Boolean>

    }

    interface View : IBase.View<ViewModel>
}