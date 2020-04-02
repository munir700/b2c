package co.yap.modules.subaccounts.paysalary.entersalaryamount

import co.yap.yapcore.IBase

interface IEnterSalaryAmount {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}