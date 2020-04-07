package co.yap.modules.subaccounts.paysalary.profile

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class HHSalaryProfileState : BaseState(), IHHSalaryProfile.State {

    override var filterCount: ObservableField<Int> = ObservableField()
    override var isTransEmpty: ObservableField<Boolean> = ObservableField(true)
}