package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.responsedtos.HouseHoldGetSubscription
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase

interface ISubscription {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun cancelSubscription(context: Context)
        var customersRepository: CustomersHHRepository
        fun getSubscriptionData()
        fun setUpSubscription()
        fun reActivateSubscription()
        var subscriptionCancelled: MutableLiveData<Boolean>
    }

    interface State : IBase.State {
        var subscriptionResponseModel: MutableLiveData<HouseHoldGetSubscription>
        var subAccount: MutableLiveData<SubAccount>
    }
}