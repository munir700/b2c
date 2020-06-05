package co.yap.modules.location.tax

import androidx.databinding.Bindable
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState


class TaxInfoState : BaseState(), ITaxInfo.State {

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

}