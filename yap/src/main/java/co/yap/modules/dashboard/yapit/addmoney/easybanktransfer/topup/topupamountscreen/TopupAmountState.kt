package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamountscreen

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class TopupAmountState : BaseState(), ITopupAmount.State {
    override val denominationChipList: MutableLiveData<List<String>> = MutableLiveData(listOf("+100","+500","+1000"))
    override val availableBalance: MutableLiveData<CharSequence> = MutableLiveData("")
    override var enteredTopUpAmount: MutableLiveData<String> = MutableLiveData()
}