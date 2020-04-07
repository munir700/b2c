package co.yap.modules.subaccounts.paysalary.profile

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase

interface IHHSalaryProfile {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
        var filterCount: ObservableField<Int>
        var isTransEmpty: ObservableField<Boolean>
    }
}