package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class TopupAmountState : BaseState(), ITopupAmount.State {
    override val denominationFirstAmount: String = "+100"
    override val denominationSecondAmount: String = "+500"
    override val denominationThirdAmount: String = "+1000"
    override val valid: MutableLiveData<Boolean> = MutableLiveData(false)
    override val availableBalance: MutableLiveData<CharSequence> = MutableLiveData("")
    override var enteredTopUpAmount: MutableLiveData<String> = MutableLiveData()
}