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
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class HHOnBoardingEmailVM @Inject constructor(
    override val state: IHHOnBoardingEmail.State,
    override var validator: Validator?, private val repository: CustomersApi
) : DaggerBaseViewModel<IHHOnBoardingEmail.State>(), IHHOnBoardingEmail.ViewModel, IValidator {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
    }

    override val clickEvent = SingleClickEvent()

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }

    override fun verifyHouseholdEmail(apiResponse: ((Boolean) -> Unit?)?) {
        launch {
            state.loading = true
            when (val response =
                repository.addHouseholdEmail(AddHouseholdEmailRequest(state.email.value ?: ""))) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        MyUserManager.user?.notificationStatuses = it
                        trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_EMAIL.type)
                        trackEventWithAttributes(MyUserManager.user, phoneNumberVerified = true)
                        trackEventWithAttributes(
                            MyUserManager.user,
                            MyUserManager.user?.creationDate
                        )
                        apiResponse?.invoke(true)
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(true)
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }
}
