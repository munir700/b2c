package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.customers.household.responsedtos.HouseHoldGetSubscription
import co.yap.yapcore.IBase

interface ISubscription {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnClick(context: Context)
        var customersRepository: CustomerHHApi
        fun getSubscriptionData()
        fun setUpSubscription()
        fun cancelSubscription()
        fun reActivateSubscription()
    }

    interface State : IBase.State {
        var subscriptionResponseModel: MutableLiveData<HouseHoldGetSubscription>
        var subAccount: MutableLiveData<SubAccount>

    }
}