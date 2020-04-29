package co.yap.modules.subaccounts.confirmation

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.IBase

interface IPaymentConfirmation {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
        var recurringPaymentScreen: MutableLiveData<Boolean>
    }
}