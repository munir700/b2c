package co.yap.household.onboarding.viewmodels

import android.app.Application
import co.yap.household.onboarding.OnboardingData
import co.yap.household.onboarding.interfaces.IOnboarding
import co.yap.household.onboarding.states.OnBoardingState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent


class OnboardingViewModel(application: Application) : BaseViewModel<IOnboarding.State>(application),
    IOnboarding.ViewModel {

    override var onboardingData: OnboardingData = OnboardingData("", "", "", "", "", "", "B2C_ACCOUNT","","")
    override val state: OnBoardingState = OnBoardingState()
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }
}