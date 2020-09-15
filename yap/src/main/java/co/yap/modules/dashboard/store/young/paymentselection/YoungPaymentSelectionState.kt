package co.yap.modules.dashboard.store.young.paymentselection

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class YoungPaymentSelectionState:BaseState(),IYoungPaymentSelection.State {
    override var selectedPlanPosition: MutableLiveData<Int> = MutableLiveData(-1)
    override var amount: MutableLiveData<String> = MutableLiveData()
}