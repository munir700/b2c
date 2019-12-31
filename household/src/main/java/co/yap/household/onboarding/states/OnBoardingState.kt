package co.yap.household.onboarding.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.household.onboarding.interfaces.IOnboarding
import co.yap.yapcore.BaseState

class OnBoardingState : BaseState(), IOnboarding.State {

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
}