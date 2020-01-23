package co.yap.household.onboarding.viewmodels

import android.app.Application
import co.yap.household.onboarding.OnboardingData
import co.yap.household.onboarding.interfaces.IOnboarding
import co.yap.household.onboarding.states.OnBoardingState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class OnboardingViewModelHouseHold(application: Application) :
    BaseViewModel<IOnboarding.State>(application),
    IOnboarding.ViewModel {

    override val state: OnBoardingState =
        OnBoardingState(application)
//    override val clickEvent: SingleClickEvent = SingleClickEvent()


    override var onboardingData: OnboardingData = OnboardingData("", "", "", "", "", "", "B2C_ACCOUNT","","")
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }
}