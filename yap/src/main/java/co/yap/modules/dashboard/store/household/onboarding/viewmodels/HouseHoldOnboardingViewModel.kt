package co.yap.modules.dashboard.store.household.onboarding.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IBaseOnboarding
import co.yap.modules.dashboard.store.household.onboarding.states.BaseOnboardingState
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class HouseHoldOnboardingViewModel(application: Application) :
    BaseViewModel<IBaseOnboarding.State>(application),
    IBaseOnboarding.ViewModel {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: BaseOnboardingState = BaseOnboardingState()
    override var username: String = ""
    override var userMobileNo: String = ""
    override var selectedPlanType: HouseHoldPlan = HouseHoldPlan()
    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }
}