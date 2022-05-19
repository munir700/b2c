package co.yap.household.onboarding.onboardemail

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.requestdtos.AddHouseholdEmailRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHOnBoardingEmailVM @Inject constructor(
    override val state: HHOnBoardingEmailState,
    private val repository: CustomersApi
) : HiltBaseViewModel<IHHOnBoardingEmail.State>(), IHHOnBoardingEmail.ViewModel, IValidator {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override var validator: Validator? = Validator(null)
    override fun handleOnClick(id: Int) {
    }

    override fun verifyHouseholdEmail(apiResponse: ((Boolean) -> Unit?)?) {
        launch {
            state.loading = true
            when (val response =
                repository.addHouseholdEmail(AddHouseholdEmailRequest(state.email.value ?: ""))) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        SessionManager.user?.notificationStatuses = it
                        trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_EMAIL.type)
                        trackEventWithAttributes(SessionManager.user, phoneNumberVerified = true)
                        trackEventWithAttributes(
                            SessionManager.user,
                            SessionManager.user?.creationDate
                        )
                        apiResponse?.invoke(true)
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(false)
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }
}
