package co.yap.modules.subaccounts.paysalary.transfer

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class HHIbanSendMoneyState : BaseState(), IHHIbanSendMoney.State {
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
}