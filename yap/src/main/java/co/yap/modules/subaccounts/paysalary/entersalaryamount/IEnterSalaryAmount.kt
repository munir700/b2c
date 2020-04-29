package co.yap.modules.subaccounts.paysalary.entersalaryamount

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase

interface IEnterSalaryAmount {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
    }
}