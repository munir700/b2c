package co.yap.modules.location.states

import androidx.databinding.Bindable
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState

class POBSelectionState : BaseState(), IPOBSelection.State {

    @get:Bindable
    override var cityOfBirth: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cityOfBirth)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

}