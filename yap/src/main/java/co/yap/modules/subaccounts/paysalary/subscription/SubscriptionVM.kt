package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.confirm
import javax.inject.Inject

class SubscriptionVM @Inject constructor(override val state: ISubscription.State) :
    DaggerBaseViewModel<ISubscription.State>()
    , ISubscription.ViewModel {
    override var customersRepository: CustomersRepository = CustomersRepository

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }
        getSubscriptionData()
    }

    override fun getSubscriptionData() {
        launch {
            state.loading = true
            when (val response =
                customersRepository.getHouseHoldSubscription(state.subAccount.value?.accountUuid)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.subscriptionResponseModel.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun setUpSubscription() {
        launch {
            state.loading = true
            when (val response =
                customersRepository.setUpHouseHoldSubscription(
                    state.subAccount.value?.accountUuid,
                    planType = state.subscriptionResponseModel.value?.planType,
                    isAutoRenew = state.subscriptionResponseModel.value?.isAutoRenew
                )) {
                is RetroApiResponse.Success -> {
                    state.subscriptionResponseModel.value?.isAutoRenew?.let {
                        state.subscriptionResponseModel.value?.isAutoRenew = !it
                    }
//                    if (state.subscriptionResponseModel.value?.isAutoRenew == true) {
//                        state.subscriptionResponseModel.value?.isAutoRenew = state.subscriptionResponseModel.value?.isAutoRenew
//                    } else {
//                        state.subscriptionResponseModel.value?.isAutoRenew = false
//                    }
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

    override fun cancelSubscription() {
        launch {
            state.loading = true
            when (val response =
                customersRepository.cancelHouseHoldSubscription(
                    state.subAccount.value?.accountUuid
                )) {
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
            cancelSubscription()
        }
    }

}