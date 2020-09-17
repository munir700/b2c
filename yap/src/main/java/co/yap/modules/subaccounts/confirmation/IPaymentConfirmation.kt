package co.yap.modules.subaccounts.confirmation

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPaymentConfirmation {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>{
    }

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
        var recurringPaymentScreen: MutableLiveData<Boolean>
        var schedulePayment: MutableLiveData<SchedulePayment>
    }
}