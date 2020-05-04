package co.yap.modules.subaccounts.paysalary.entersalaryamount

import android.content.Context
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase

interface IEnterSalaryAmount {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun onAmountChange(amount: CharSequence, start: Int, before: Int, count: Int)
        fun createSchedulePayment(uuid: String?,schedulePayment: SchedulePayment?)
        fun handlePressOnClick(id: Int)
    }

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
        var amount: MutableLiveData<String>
        var isRecurring: MutableLiveData<Boolean>
        var isValid: MutableLiveData<Boolean>
    }
}