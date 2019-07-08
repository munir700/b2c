package co.yap.modules.onboarding.interfaces

import co.yap.modules.onboarding.models.OnboardingData
import co.yap.yapcore.IBase

interface IOnboarding {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        var onboardingData: OnboardingData
    }

    interface State : IBase.State {
        var totalProgress: Int
        var currentProgress: Int
    }
}