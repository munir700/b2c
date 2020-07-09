package co.yap.modules.subaccounts.paysalary.recurringpayment

import android.content.Context
import android.widget.CompoundButton
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IRecurringPayment {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun datePicker()
        var fragmentManager: FragmentManager?
        fun createSchedulePayment(uuid: String?, schedulePayment: SchedulePayment?)
        fun cancelSchedulePayment(scheduledPaymentUuid: String?)
        fun updateSchedulePayment(scheduledPaymentUuid: String?)
        fun handlePressOnClick(id: Int)
        fun onAmountChange(amount: CharSequence, start: Int, before: Int, count: Int)
        fun onCheckedChanged(text: String, isChecked: Boolean)
        val GO_TO_CONFIRMATION: Int get() = 3
        val clickEvent: SingleClickEvent

    }

    interface State : IBase.State {
        var date: MutableLiveData<String>
        var subAccount: MutableLiveData<SubAccount>
        var schedulePayment: MutableLiveData<SchedulePayment>
        var amount: MutableLiveData<String>
        var recurringInterval: MutableLiveData<String>
        var recurringTransaction: MutableLiveData<SchedulePayment>?
    }
}