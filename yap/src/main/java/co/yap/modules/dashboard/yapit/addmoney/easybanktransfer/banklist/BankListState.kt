package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class BankListState : BaseState(),
    IBankList.State {
    override var isSearchActive: MutableLiveData<Boolean> = MutableLiveData(false)
}