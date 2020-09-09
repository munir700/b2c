package co.yap.modules.subaccounts.confirmation.confirmationsuccess

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IConfirmationSuccess {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
    }

    interface State : IBase.State {
        var schedulePayment: MutableLiveData<SchedulePayment>
    }
}
