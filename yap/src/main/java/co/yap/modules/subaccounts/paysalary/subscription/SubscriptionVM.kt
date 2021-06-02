package co.yap.modules.subaccounts.paysalary.subscription

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.State
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.leanplum.HHUserActivityEvents
import co.yap.yapcore.leanplum.trackEvent
import javax.inject.Inject

class SubscriptionVM @Inject constructor(override val state: ISubscription.State) :
    DaggerBaseViewModel<ISubscription.State>(), ISubscription.ViewModel {
    override var customersRepository: CustomersHHRepository = CustomersHHRepository
    override var subscriptionCancelled: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let { state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName) }
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
                customersRepository.reActivateHouseHoldSubscription(state.subAccount.value?.accountUuid)) {
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

    override fun cancelSubscription(context: Context) {
        context.confirm(
            title = getString(Strings.screen_household_cancel_subscription_cancel_title),
            message = getString(Strings.screen_household_cancel_subscription_cancel_message),
            positiveButton = getString(Strings.screen_household_cancel_subscription_yes_button),
            negativeButton = getString(Strings.screen_household_cancel_subscription_no_button),
            cancelable = false
        ) {
            launch {
                state.loading = true
                when (val response =
                    customersRepository.cancelHouseHoldSubscription(
                        state.subAccount.value?.accountUuid
                    )) {
                    is RetroApiResponse.Success -> {
                        subscriptionCancelled.value = true
                        trackEvent(HHUserActivityEvents.HH_SUBS_CANCEL.type)
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
    }

    override fun handleOnClick(id: Int) {
    }
}