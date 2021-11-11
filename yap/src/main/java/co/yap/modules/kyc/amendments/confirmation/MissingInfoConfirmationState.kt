package co.yap.modules.kyc.amendments.confirmation

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class MissingInfoConfirmationState() : BaseState(), IMissingInfoConfirmation.State {
    override val subTitle: ObservableField<String> = ObservableField()
}