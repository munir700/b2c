package co.yap.modules.subaccounts.paysalary.profile

import co.yap.yapcore.IBase

interface IHHSalaryProfile {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State
}