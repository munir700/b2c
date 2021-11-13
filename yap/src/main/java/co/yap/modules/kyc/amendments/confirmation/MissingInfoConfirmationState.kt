package co.yap.modules.kyc.amendments.confirmation

import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.Section
import co.yap.yapcore.BaseState

class MissingInfoConfirmationState() : BaseState(), IMissingInfoConfirmation.State {
    override val subTitle: ObservableField<String> = ObservableField()
    override var missingInfoMap: HashMap<Section?, List<String>?>? = null
}