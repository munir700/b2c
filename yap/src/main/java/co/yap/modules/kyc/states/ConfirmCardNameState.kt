package co.yap.modules.kyc.states

import androidx.databinding.ObservableField
import co.yap.modules.kyc.interfaces.IConfirmCardName
import co.yap.yapcore.BaseState

class ConfirmCardNameState : IConfirmCardName.State, BaseState() {
    override var fullName: ObservableField<String> = ObservableField()
}