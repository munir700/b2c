package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IOnboarding
import co.yap.modules.onboarding.models.OnboardingData
import co.yap.modules.onboarding.states.OnboardingState
import co.yap.yapcore.BaseViewModel

class OnboardingViewModel(application: Application) : BaseViewModel<IOnboarding.State>(application),
    IOnboarding.ViewModel {
    override var onboardingData: OnboardingData = OnboardingData("", "", "", "", "", "", AccountType.B2C_ACCOUNT)

    override val state: OnboardingState = OnboardingState()

    override fun handlePressOnBackButton() {

    }

    override fun handlePressOnTickButton() {

    }
}