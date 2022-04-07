package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class TopupAmountState : BaseState(), ITopupAmount.State {
    override val denominationChipList: MutableLiveData<List<String>> = MutableLiveData(listOf("+100","+500","+1000"))
    override val valid: MutableLiveData<Boolean> = MutableLiveData(false)
    override val availableBalance: MutableLiveData<CharSequence> = MutableLiveData()
}