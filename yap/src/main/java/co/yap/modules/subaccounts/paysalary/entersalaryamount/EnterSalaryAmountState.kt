package co.yap.modules.subaccounts.paysalary.entersalaryamount

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class EnterSalaryAmountState : BaseState(), IEnterSalaryAmount.State {
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var amount: MutableLiveData<String> = MutableLiveData()
    override var isRecurring: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isValid: MutableLiveData<Boolean> = MutableLiveData(false)
    override var lastTransaction: MutableLiveData<SalaryTransaction>? = MutableLiveData()
}
