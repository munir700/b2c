package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.yapcore.BaseState

class CongratulationsState : BaseState(), ICongratulations.State {


    @get:Bindable
    override var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    override var ibanNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.ibanNumber)
        }


    @get:Bindable
    override var onboardingTime: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.onboardingTime)
        }

}