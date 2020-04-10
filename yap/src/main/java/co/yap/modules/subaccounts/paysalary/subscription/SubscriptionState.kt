package co.yap.modules.subaccounts.paysalary.subscription

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.networking.customers.responsedtos.household.HouseHoldGetSubscriptionResponseDTO
import co.yap.yapcore.BaseState

class SubscriptionState : BaseState(), ISubscription.State {
    @get:Bindable
    override var subscriptionResponseModel: HouseHoldGetSubscriptionResponseDTO? =
        HouseHoldGetSubscriptionResponseDTO()
        set(value) {
            field = value
            notifyPropertyChanged(BR.subscriptionResponseModel)
        }


}