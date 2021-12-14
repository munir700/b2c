package co.yap.modules.location.fragments.confirmation

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class MissingInfoConfirmationState() : BaseState(), IMissingInfoConfirmation.State {
    override val title: ObservableField<String> = ObservableField()
    override val subTitle: ObservableField<String> = ObservableField()
    override var missingInfoMap: HashMap<String?, List<String>?>? = null
}