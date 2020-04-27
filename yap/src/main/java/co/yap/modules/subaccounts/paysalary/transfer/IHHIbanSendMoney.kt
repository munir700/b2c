package co.yap.modules.subaccounts.paysalary.transfer

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.IBase

interface IHHIbanSendMoney {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
    }
}
