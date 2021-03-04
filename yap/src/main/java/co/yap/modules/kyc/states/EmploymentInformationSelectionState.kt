package co.yap.modules.kyc.states

import androidx.databinding.ObservableBoolean
import co.yap.modules.kyc.interfaces.IEmpInfoSelection
import co.yap.yapcore.BaseState

class EmploymentInformationSelectionState : BaseState(), IEmpInfoSelection.State {
    override val enableNextButton: ObservableBoolean = ObservableBoolean()
}
