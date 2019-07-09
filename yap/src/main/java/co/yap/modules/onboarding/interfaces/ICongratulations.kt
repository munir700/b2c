package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase

interface ICongratulations {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var elapsedOnboardingTime: Long
        fun handlePressOnCompleteVerification()
    }

    interface State : IBase.State {
        var name: String
        var ibanNumber: String
        var onboardingTime: String
    }
}