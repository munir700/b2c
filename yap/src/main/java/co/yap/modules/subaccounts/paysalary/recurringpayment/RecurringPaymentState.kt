package co.yap.modules.subaccounts.paysalary.recurringpayment

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class RecurringPaymentState : BaseState(), IRecurringPayment.State {
    override var date: ObservableField<String> = ObservableField()
    override var schedulePayment: MutableLiveData<SchedulePayment> = MutableLiveData()
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var isValid: MutableLiveData<Boolean> = MutableLiveData(false)
    override var amount: MutableLiveData<String> = MutableLiveData()
}