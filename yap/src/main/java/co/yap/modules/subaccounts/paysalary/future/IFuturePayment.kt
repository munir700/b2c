package co.yap.modules.subaccounts.paysalary.future

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IFuturePayment {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun datePicker(view: android.view.View)
        var fragmentManager: FragmentManager?
        fun onAmountChange(amount: CharSequence, start: Int, before: Int, count: Int)
        fun createSchedulePayment(uuid: String?, schedulePayment: SchedulePayment?)
        fun cancelSchedulePayment(scheduledPaymentUuid: String?)
        fun updateSchedulePayment(scheduledPaymentUuid: String?, request: SchedulePayment)
        fun handlePressOnClick(id: Int)
        val clickEvent: SingleClickEvent
        val GO_TO_CONFIRMATION: Int get() = 3
    }

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
        var date: MutableLiveData<String>
        var isValid: MutableLiveData<Boolean>
        var amount: MutableLiveData<String>
        var futureTransaction: MutableLiveData<SchedulePayment>?
    }
}