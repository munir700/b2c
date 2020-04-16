package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.State
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
            publishState(State.loading(null))
            when (val response =
                customersRepository.getHouseHoldSubscription(state.subAccount.value?.accountUuid)) {
                is RetroApiResponse.Success -> {
                    publishState(State.success(null))
                    state.subscriptionResponseModel.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    publishState(State.empty(response.error.message))
                }
            }
        }
    }

    override fun setUpSubscription() {
        launch {
            val isRenew = state.subscriptionResponseModel.value?.isAutoRenew?.let {
                !it
            }
            state.loading = true
            when (val response =
                customersRepository.setUpHouseHoldSubscription(
                    state.subAccount.value?.accountUuid,
                    planType = state.subscriptionResponseModel.value?.planType,
                    isAutoRenew = isRenew
                )) {
                is RetroApiResponse.Success -> {
                    state.subscriptionResponseModel.value?.isAutoRenew?.let {
                        state.subscriptionResponseModel.value?.isAutoRenew = !it
                    }
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
                    getSubscriptionData()
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
                    getSubscriptionData()
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