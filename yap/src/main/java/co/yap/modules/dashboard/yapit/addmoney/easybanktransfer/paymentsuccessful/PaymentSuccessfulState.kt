package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.paymentsuccessful

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class PaymentSuccessfulState : BaseState(),
    IPaymentSuccessful.State{
    override var newBalanceText: MutableLiveData<CharSequence> = MutableLiveData()
}