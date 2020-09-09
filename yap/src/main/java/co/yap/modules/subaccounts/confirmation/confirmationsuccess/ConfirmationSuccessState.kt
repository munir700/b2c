package co.yap.modules.subaccounts.confirmation.confirmationsuccess

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.yapcore.BaseState

class ConfirmationSuccessState : BaseState(), IConfirmationSuccess.State {
    override var schedulePayment: MutableLiveData<SchedulePayment> = MutableLiveData()
}