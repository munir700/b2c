package co.yap.modules.dashboard.store.household.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.store.household.interfaces.IHouseHoldSubscription
import co.yap.yapcore.BaseState

class HouseHoldSubscriptionState : BaseState(), IHouseHoldSubscription.State {

    @get:Bindable
    override var monthlyFee: String = "AED 59.99"
        set(value) {
            field = value
            notifyPropertyChanged(BR.monthlyFee)

        }

    @get:Bindable
    override var annuallyFee: String = "AED 720.00"
        set(value) {
            field = value
            notifyPropertyChanged(BR.annuallyFee)

        }

    @get:Bindable
    override var subscriptionFee: String = "AED 19.99/month"
        set(value) {
            field = value
            notifyPropertyChanged(BR.subscriptionFee)

        }

    @get:Bindable
    override var hasSelectedPackage: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasSelectedPackage)
            if (hasSelectedPackage) {
                valid = true
            }
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }
}