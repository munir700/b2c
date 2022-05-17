package co.yap.modules.subaccounts.paysalary.transfer

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHIbanSendMoneyState  @Inject constructor(): BaseState(), IHHIbanSendMoney.State {
    override var amount: MutableLiveData<String>? = MutableLiveData()
    override var selectedTxnCategoryPosition: MutableLiveData<Int> = MutableLiveData(-1)
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var availableBalance: MutableLiveData<String>? = MutableLiveData("0.00")
        set(value) {
            field = value
            notifyChange()
        }
    override var isRecurringPayment: MutableLiveData<Boolean>? = MutableLiveData()
}