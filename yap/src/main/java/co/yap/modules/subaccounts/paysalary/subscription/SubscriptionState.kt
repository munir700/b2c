package co.yap.modules.subaccounts.paysalary.subscription

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.networking.customers.responsedtos.household.HouseHoldGetSubscription
import co.yap.yapcore.BaseState

class SubscriptionState : BaseState(), ISubscription.State {
    override var subscriptionResponseModel: MutableLiveData<HouseHoldGetSubscription> =
        MutableLiveData()
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
}