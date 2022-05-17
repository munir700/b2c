package co.yap.modules.subaccounts.confirmation

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState
import javax.inject.Inject

class PaymentConfirmationState @Inject constructor(): BaseState(), IPaymentConfirmation.State {
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var recurringPaymentScreen: MutableLiveData<Boolean> = MutableLiveData()
    override var schedulePayment: MutableLiveData<SchedulePayment> = MutableLiveData()
}