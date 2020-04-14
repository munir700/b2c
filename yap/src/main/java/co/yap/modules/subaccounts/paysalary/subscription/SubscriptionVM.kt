package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.toast
import javax.inject.Inject

class SubscriptionVM @Inject constructor(override val state: ISubscription.State) :
    DaggerBaseViewModel<ISubscription.State>()
    , ISubscription.ViewModel {
    override var customersRepository: CustomersRepository = CustomersRepository

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getSubscriptionData()
    }

    override fun getSubscriptionData() {
        launch {
            state.loading = true
            when (val response =
                customersRepository.getHouseHoldSubscription("f0c52305-a055-498d-8d79-71cf815dcaff")) {
                is RetroApiResponse.Success -> {
                    state.subscriptionResponseModel.set(response.data.data)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }
    override fun reActivateSubscription() {
        launch {
            state.loading = true
            when (val response =
                customersRepository.reActivateHouseHoldSubscription("f0c52305-a055-498d-8d79-71cf815dcaff")) {
                is RetroApiResponse.Success -> {

                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }


    override fun handlePressOnClick(context: Context) {
        context.confirm(
            title = "Are you sure you want to cancel this subscription?",
            message = "Cancellation will come into effect following the 12-month contract period",
            positiveButton = "YES",
            negativeButton = "NO",
            cancelable = false
        ) {
            toast(context, "Cancel subscription.")
        }
    }

}