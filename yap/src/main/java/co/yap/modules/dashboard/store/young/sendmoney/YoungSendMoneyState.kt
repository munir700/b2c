package co.yap.modules.dashboard.store.young.sendmoney

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class YoungSendMoneyState : BaseState(), IYoungSendMoney.State {
    override var amount: MutableLiveData<String> = MutableLiveData()

}