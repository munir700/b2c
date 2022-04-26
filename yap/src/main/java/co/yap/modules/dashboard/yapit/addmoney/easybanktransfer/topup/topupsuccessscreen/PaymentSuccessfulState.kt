package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupsuccessscreen

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class PaymentSuccessfulState : BaseState(),
    IPaymentSuccessful.State {
    override var newBalanceText: MutableLiveData<CharSequence> = MutableLiveData()
}