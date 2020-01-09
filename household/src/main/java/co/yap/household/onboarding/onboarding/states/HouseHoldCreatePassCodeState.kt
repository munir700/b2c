package co.yap.household.onboarding.onboarding.states

import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCreatePassCode
import co.yap.yapcore.BaseState

class HouseHoldCreatePassCodeState : BaseState(), IHouseHoldCreatePassCode.State {
    @get:Bindable
    override var buttonValidation: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonValidation)
        }
    @get:Bindable
    override var dialerError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dialerError)
        }
}