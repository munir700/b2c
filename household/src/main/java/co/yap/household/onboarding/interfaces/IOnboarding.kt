package co.yap.household.onboarding.interfaces

import co.yap.household.onboarding.OnboardingData
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent


interface IOnboarding {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        var onboardingData: OnboardingData
        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface State : IBase.State {
        var totalProgress: Int
        var currentProgress: Int
        var currentBackground: Int
        var existingYapUser: Boolean?
        var accountInfo: AccountInfo?
    }
}