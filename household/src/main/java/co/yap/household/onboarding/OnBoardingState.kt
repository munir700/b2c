package co.yap.household.onboarding

import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.yapcore.BaseState

class OnBoardingState : BaseState(), IOnboarding.State {

    @get:Bindable
    override var myName: String = "House Hold"
        set(value) {
            field = value
            notifyPropertyChanged(BR.myName)
            println(myName)
            println(myName)

        }

}