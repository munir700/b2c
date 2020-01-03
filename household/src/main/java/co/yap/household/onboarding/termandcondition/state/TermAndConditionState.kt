package co.yap.household.onboarding.termandcondition.state

import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.household.onboarding.termandcondition.interfaces.ITermAndCondition
import co.yap.yapcore.BaseState

class TermAndConditionState : BaseState(), ITermAndCondition.State {

    @get:Bindable
    override var toolbarVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolbarVisibility)

        }

}