package co.yap.modules.dashboard.store.young.paymentselection

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IYoungPaymentSelection {
    interface State : IBase.State {
        var selectedPlanPosition: MutableLiveData<Int>
        var amount:MutableLiveData<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel>
}