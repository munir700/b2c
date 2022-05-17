package co.yap.modules.subaccounts.paysalary.recurringpayment

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState
import javax.inject.Inject

class RecurringPaymentState @Inject constructor(): BaseState(), IRecurringPayment.State {
    override var date: MutableLiveData<String> = MutableLiveData()
    override var schedulePayment: MutableLiveData<SchedulePayment> = MutableLiveData()
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var amount: MutableLiveData<String> = MutableLiveData()
    override var recurringInterval: MutableLiveData<String> = MutableLiveData()
    override var recurringTransaction: MutableLiveData<SchedulePayment>? = MutableLiveData()
}