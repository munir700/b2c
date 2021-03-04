package co.yap.modules.kyc.states

import androidx.databinding.ObservableBoolean
import co.yap.modules.kyc.interfaces.IEmploymentStatusSelection
import co.yap.yapcore.BaseState

class EmploymentStatusSelectionState : BaseState(), IEmploymentStatusSelection.State {
    override val enableNextButton: ObservableBoolean = ObservableBoolean()
}
