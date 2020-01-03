package co.yap.modules.termandcondition.state

import androidx.databinding.Bindable
import co.yap.modules.termandcondition.interfaces.ITermAndCondition
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState

class TermAndConditionState : BaseState(), ITermAndCondition.State {

    @get:Bindable
    override var toolbarVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolbarVisibility)

        }

}