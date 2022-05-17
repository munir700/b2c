package co.yap.modules.subaccounts.paysalary.future

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState
import javax.inject.Inject

class FuturePaymentState @Inject constructor() : BaseState(), IFuturePayment.State {
    override var date: MutableLiveData<String> = MutableLiveData()
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var amount: MutableLiveData<String> = MutableLiveData()
    override var isValid: MutableLiveData<Boolean> = MutableLiveData(false)
    override var futureTransaction: MutableLiveData<SchedulePayment>? = MutableLiveData()
}
