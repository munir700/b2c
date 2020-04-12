package co.yap.modules.subaccounts.paysalary.subscription

import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.household.HouseHoldGetSubscription
import co.yap.yapcore.BaseState

class SubscriptionState : BaseState(), ISubscription.State {
    override var subscriptionResponseModel: ObservableField<HouseHoldGetSubscription> =
        ObservableField()
}