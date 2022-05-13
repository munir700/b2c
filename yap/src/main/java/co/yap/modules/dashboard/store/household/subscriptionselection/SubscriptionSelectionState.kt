package co.yap.modules.dashboard.store.household.subscriptionselection

import androidx.lifecycle.MutableLiveData
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.BaseState
import javax.inject.Inject

class SubscriptionSelectionState @Inject constructor() : BaseState(), ISubscriptionSelection.State {
    override var monthlyFee: MutableLiveData<String> = MutableLiveData("AED 0.00")
    override var annuallyFee: MutableLiveData<String> = MutableLiveData("AED 0.00")
    override var selectedPlanPosition: MutableLiveData<Int> = MutableLiveData(-1)
    override var planDiscount: MutableLiveData<String>? = MutableLiveData()
    override var plansList: MutableLiveData<MutableList<HouseHoldPlan>> = MutableLiveData()
}
