package co.yap.modules.employmentstatusselection

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.BaseState

class EmploymentStatusSelectionState : BaseState(), IEmploymentStatusSelection.State {
    override val enableNextButton: ObservableBoolean = ObservableBoolean()
}
