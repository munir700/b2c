package co.yap.modules.subaccounts.paysalary.employee

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayHHEmployeeSalary {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
    }

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
    }
}
