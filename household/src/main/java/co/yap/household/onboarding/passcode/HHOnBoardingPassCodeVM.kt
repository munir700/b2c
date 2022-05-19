package co.yap.household.onboarding.passcode

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.requestdtos.CreatePassCodeRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHOnBoardingPassCodeVM @Inject constructor(
    override val state: HHOnBoardingPassCodeState,
    private val repository: CustomersApi
) :
    HiltBaseViewModel<IHHOnBoardingPassCode.State>(), IHHOnBoardingPassCode.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }

    override fun createPassCodeRequest(apiResponse: ((Boolean?) -> Unit?)?) {
        launch {
            state.loading = true
            when (val response =
                repository.createHouseholdPasscode(
                    CreatePassCodeRequest(
                        passcode = state.passCode.value ?: ""
                    )
                )) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        SessionManager.user?.notificationStatuses = it
                        apiResponse?.invoke(true)
                        trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_PASSCODE_CREATED.type)
                    }
                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(false)
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }
}