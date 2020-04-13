package co.yap.modules.subaccounts.paysalary.profile.cardholderprofile

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.Customer
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.IBase

interface IHHProfile {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State{
        var customer: MutableLiveData<Customer>
    }
}