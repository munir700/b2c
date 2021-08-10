package co.yap.modules.kyc.states

import androidx.databinding.ObservableField
import co.yap.modules.kyc.interfaces.IEditCardName
import co.yap.yapcore.BaseState
import co.yap.yapcore.managers.SessionManager

class EditCardNameState : BaseState(), IEditCardName.State {
    override var fullName: ObservableField<String> = ObservableField()
    override var CardPrefix: ObservableField<String> =
        ObservableField(SessionManager.card.value?.cardSerialNumber?.substring(0, 3) ?: "")
}