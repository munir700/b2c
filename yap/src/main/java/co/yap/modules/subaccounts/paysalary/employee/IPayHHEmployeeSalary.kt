package co.yap.modules.subaccounts.paysalary.employee

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayHHEmployeeSalary {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var customersHHRepository: CustomerHHApi
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
        fun getLastTransaction(uuid: String?)
    }

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
    }
}
