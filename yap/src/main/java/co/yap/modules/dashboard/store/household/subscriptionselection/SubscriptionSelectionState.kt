package co.yap.modules.dashboard.store.household.subscriptionselection

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class SubscriptionSelectionState : BaseState(), ISubscriptionSelection.State {
    override var monthlyFee: MutableLiveData<String> = MutableLiveData("AED 0.00")
    override var annuallyFee: MutableLiveData<String> = MutableLiveData("AED 0.00")
    override var subscriptionFee: MutableLiveData<String> = MutableLiveData("AED 19.99/month")
    override var planDiscount: MutableLiveData<String>? = MutableLiveData()
    override var valid: MutableLiveData<Boolean> = MutableLiveData(false)

    @get:Bindable
    override var hasSelectedPackage: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasSelectedPackage)
            if (hasSelectedPackage) {
                valid.value = true
            }
        }
}
