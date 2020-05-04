package co.yap.modules.subaccounts.paysalary.subscription

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.customers.household.responsedtos.HouseHoldGetSubscription
import co.yap.yapcore.BaseState

class SubscriptionState : BaseState(), ISubscription.State {
    override var subscriptionResponseModel: MutableLiveData<HouseHoldGetSubscription> =
        MutableLiveData()
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
}