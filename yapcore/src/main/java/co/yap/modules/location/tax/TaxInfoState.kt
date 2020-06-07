package co.yap.modules.location.tax

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState


class TaxInfoState : BaseState(), ITaxInfo.State {
    override var valid: ObservableField<Boolean> = ObservableField(false)
}