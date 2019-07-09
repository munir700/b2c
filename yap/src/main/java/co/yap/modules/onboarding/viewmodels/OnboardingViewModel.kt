package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IOnboarding
import co.yap.modules.onboarding.models.OnboardingData
import co.yap.modules.onboarding.states.OnboardingState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class OnboardingViewModel(application: Application) : BaseViewModel<IOnboarding.State>(application),
    IOnboarding.ViewModel {

    override var onboardingData: OnboardingData = OnboardingData("", "", "", "", "", "", AccountType.B2C_ACCOUNT,"")
    override val state: OnboardingState = OnboardingState()
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }
}