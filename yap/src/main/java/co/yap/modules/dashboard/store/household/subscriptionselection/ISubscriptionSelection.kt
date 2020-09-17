package co.yap.modules.dashboard.store.household.subscriptionselection

import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.networking.transactions.TransactionsApi
import co.yap.networking.transactions.TransactionsRepository
import co.yap.widgets.radiocus.PresetRadioGroup
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISubscriptionSelection {
    interface State : IBase.State {
        var monthlyFee: MutableLiveData<String>
        var annuallyFee: MutableLiveData<String>
        var selectedPlanPosition: MutableLiveData<Int>
        var planDiscount: MutableLiveData<String>?
        var plansList: MutableLiveData<MutableList<HouseHoldPlan>>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val repository: TransactionsApi
        fun fetchSubscriptionsFee()
        var onCheckedChangeListener: PresetRadioGroup.OnCheckedChangeListener?
    }

    interface View : IBase.View<ViewModel>
}
