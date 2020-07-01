package co.yap.modules.subaccounts.paysalary.entersalaryamount

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.transactions.requestdtos.PaySalaryNowRequest
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IEnterSalaryAmount {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun onAmountChange(amount: CharSequence, start: Int, before: Int, count: Int)
//        fun createSchedulePayment(uuid: String?, schedulePayment: SchedulePayment?)
        fun paySalaryNow(request: PaySalaryNowRequest)
        fun handlePressOnClick(id: Int)
        val clickEvent: SingleClickEvent
        val GO_TO_CONFIRMATION: Int get() = 3
        val GO_TO_RECURING: Int get() = 4
    }

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
        var amount: MutableLiveData<String>
        var isRecurring: MutableLiveData<Boolean>
        var isValid: MutableLiveData<Boolean>
        var lastTransaction: MutableLiveData<SalaryTransaction>?
    }
}
