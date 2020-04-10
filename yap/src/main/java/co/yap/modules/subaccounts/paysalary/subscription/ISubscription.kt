package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import androidx.databinding.ObservableField
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.household.HouseHoldGetSubscriptionResponseDTO
import co.yap.yapcore.IBase

interface ISubscription {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnClick(context: Context)
        var customersRepository: CustomersRepository
        fun getSubscriptionData()
    }

    interface State : IBase.State {
        var subscriptionResponseModel: HouseHoldGetSubscriptionResponseDTO?

    }
}