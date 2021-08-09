package co.yap.modules.kyc.states

import androidx.databinding.ObservableField
import co.yap.modules.kyc.interfaces.IEditCardName
import co.yap.yapcore.BaseState

class EditCardNameState : BaseState(), IEditCardName.State {
    override var fullName: ObservableField<String> = ObservableField()
}