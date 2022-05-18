package co.yap.modules.dashboard.store.young.sendmoney

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class YoungSendMoneyState @Inject constructor() : BaseState(), IYoungSendMoney.State {
    override var amount: MutableLiveData<String> = MutableLiveData()
}