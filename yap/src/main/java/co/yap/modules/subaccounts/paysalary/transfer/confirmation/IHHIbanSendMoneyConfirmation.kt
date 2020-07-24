package co.yap.modules.subaccounts.paysalary.transfer.confirmation

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHIbanSendMoneyConfirmation {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
    }

    interface State : IBase.State{
        var subAccount: MutableLiveData<SubAccount>
        var request: MutableLiveData<IbanSendMoneyRequest>

    }
}