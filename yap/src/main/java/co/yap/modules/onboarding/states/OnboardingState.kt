package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.modules.onboarding.enums.OnboardingPhase
import co.yap.modules.onboarding.interfaces.IOnboarding
import co.yap.yapcore.BaseState

class OnboardingState : BaseState(), IOnboarding.State {

    @get:Bindable
    override var totalProgress: Int = 100
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalProgress)
        }

    @get:Bindable
    override var currentProgress: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentProgress)
        }

    @get:Bindable
    override var emailError: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailError)
        }
    override var notificationAction: MutableLiveData<OnboardingPhase> =
        MutableLiveData(OnboardingPhase.NONE)
    override var anyNotificationOptionSelected: ObservableBoolean = ObservableBoolean(false)
}