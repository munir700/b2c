package co.yap.modules.subaccounts.paysalary.transfer

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.transactions.TransactionsApi
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.household.TransactionsHHApi
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHIbanSendMoney {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        var transactionsRepository: TransactionsHHApi
        fun onAmountChange(amount: CharSequence, start: Int, before: Int, count: Int)
        fun ibanSendMoney(request: IbanSendMoneyRequest, apiResponse: ((Boolean) -> Unit?)?)
        fun getSchedulePayment(uuid: String?,apiResponse: ((SchedulePayment?) -> Unit?)?)
    }

    interface State : IBase.State {
        var amount: MutableLiveData<String>?
        var selectedTxnCategoryPosition: MutableLiveData<Int>
        var subAccount: MutableLiveData<SubAccount>
        var availableBalance: MutableLiveData<String>?
        var isRecurringPayment: MutableLiveData<Boolean>?
    }
}
