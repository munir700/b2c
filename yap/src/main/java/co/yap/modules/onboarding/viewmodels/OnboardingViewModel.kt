package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IOnboarding
import co.yap.modules.onboarding.states.OnboardingState
import co.yap.yapcore.BaseViewModel

class OnboardingViewModel(application: Application) : BaseViewModel<IOnboarding.State>(application), IOnboarding.ViewModel {

    override val state: IOnboarding.State
        get() = OnboardingState()
}