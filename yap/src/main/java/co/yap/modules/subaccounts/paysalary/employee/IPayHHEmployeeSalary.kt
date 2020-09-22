package co.yap.modules.subaccounts.paysalary.employee

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.transactions.household.TransactionsHHApi
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayHHEmployeeSalary {
    interface View : IBase.View<ViewModel>


    interface ViewModel : IBase.ViewModel<State> {
        var customersHHRepository: CustomerHHApi
        var transactionsHHRepository: TransactionsHHApi
        fun getLastTransaction(uuid: String?)
        fun getSchedulePayment(uuid: String?)
    }

    interface State : IBase.State {
        var subAccount: MutableLiveData<SubAccount>
        var lastTransaction: MutableLiveData<SalaryTransaction>?
        var futureTransaction: MutableLiveData<SchedulePayment>?
        var recurringTransaction: MutableLiveData<SchedulePayment>?
//        var scheduleTransaction: MutableLiveData<MutableList<SalaryTransaction>>?
    }
}
