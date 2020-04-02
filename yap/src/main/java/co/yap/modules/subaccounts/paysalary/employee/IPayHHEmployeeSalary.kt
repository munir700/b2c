package co.yap.modules.subaccounts.paysalary.employee

import co.yap.yapcore.IBase

interface IPayHHEmployeeSalary {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}