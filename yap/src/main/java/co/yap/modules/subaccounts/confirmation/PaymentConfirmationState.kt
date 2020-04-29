package co.yap.modules.subaccounts.confirmation

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class PaymentConfirmationState : BaseState(), IPaymentConfirmation.State {
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var recurringPaymentScreen: MutableLiveData<Boolean> = MutableLiveData()

}