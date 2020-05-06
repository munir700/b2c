package co.yap.modules.subaccounts.paysalary.employee

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class PayHHEmployeeSalaryState : BaseState(), IPayHHEmployeeSalary.State {
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
}
