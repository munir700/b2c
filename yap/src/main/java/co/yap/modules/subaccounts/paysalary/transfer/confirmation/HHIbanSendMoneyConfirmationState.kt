package co.yap.modules.subaccounts.paysalary.transfer.confirmation

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.yapcore.BaseState

class HHIbanSendMoneyConfirmationState: BaseState(), IHHIbanSendMoneyConfirmation.State {
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var request: MutableLiveData<IbanSendMoneyRequest> = MutableLiveData()
}