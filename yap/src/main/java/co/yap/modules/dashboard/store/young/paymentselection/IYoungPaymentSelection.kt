package co.yap.modules.dashboard.store.young.paymentselection

import androidx.lifecycle.MutableLiveData
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.widgets.radiocus.PresetRadioGroup
import co.yap.yapcore.IBase

class IYoungPaymentSelection {
    interface State : IBase.State {
        var selectedPlanPosition: MutableLiveData<Int>
        var amount: MutableLiveData<String>
        var monthlyFee: MutableLiveData<String>
        var annuallyFee: MutableLiveData<String>
        var planDiscount: MutableLiveData<String>?
        var plansList: MutableLiveData<MutableList<HouseHoldPlan>>
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun fetchSubscriptionsFee()
        var onCheckedChangeListener: PresetRadioGroup.OnCheckedChangeListener?
    }

    interface View : IBase.View<ViewModel>
}