package co.yap.modules.dashboard.store.household.subscriptionselection

import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISubscriptionSelection {
    interface State : IBase.State {
        var monthlyFee: MutableLiveData<String>
        var annuallyFee: MutableLiveData<String>
        var subscriptionFee: MutableLiveData<String>
        var hasSelectedPackage: Boolean
        var planDiscount: MutableLiveData<String>?
        var valid: MutableLiveData<Boolean>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val repository: TransactionsRepository
        val clickEvent: SingleClickEvent
        var benefitsList: ArrayList<BenefitsModel>
        var plansList: ArrayList<HouseHoldPlan>
        fun handlePressOnButton(id: Int)
        fun loadDummyData(): ArrayList<BenefitsModel>
        fun fetchHouseholdPackagesFee()
    }

    interface View : IBase.View<ViewModel>
}
