package co.yap.modules.kyc.amendments.confirmation.states

import androidx.databinding.ObservableField
import co.yap.modules.kyc.amendments.confirmation.interfaces.IMissingInfoConfirmation
import co.yap.yapcore.BaseState

class MissingInfoConfirmationState() : BaseState(), IMissingInfoConfirmation.State {
    override val subTitle: ObservableField<String> = ObservableField()
}