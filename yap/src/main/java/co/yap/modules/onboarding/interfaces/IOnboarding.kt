package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.modules.onboarding.enums.OnboardingPhase
import co.yap.modules.onboarding.models.OnboardingData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IOnboarding {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        var onboardingData: OnboardingData
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        val isPhoneNumberEntered: MutableLiveData<Boolean>
        var rankNo: MutableLiveData<String>
        var isWaitingList: MutableLiveData<Boolean>
        var emailError: MutableLiveData<String>
    }

    interface State : IBase.State {
        var totalProgress: Int
        var currentProgress: Int
        var emailError: Boolean
        var notificationAction: MutableLiveData<OnboardingPhase>
        var anyNotificationOptionSelected : ObservableBoolean
    }
}