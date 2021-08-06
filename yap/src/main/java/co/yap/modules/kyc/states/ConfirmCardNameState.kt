package co.yap.modules.kyc.states

import co.yap.modules.kyc.interfaces.IConfirmCardName
import co.yap.yapcore.BaseState

class ConfirmCardNameState : IConfirmCardName.State, BaseState() {
    override var fullName: String = "Nada Hassan"
}