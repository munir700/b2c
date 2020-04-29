package co.yap.modules.subaccounts.paysalary.entersalaryamount

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class EnterSalaryAmountState : BaseState(), IEnterSalaryAmount.State {
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()

}