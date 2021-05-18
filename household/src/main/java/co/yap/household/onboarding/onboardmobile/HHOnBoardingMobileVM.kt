package co.yap.household.onboarding.onboardmobile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.requestdtos.VerifyHouseholdMobileRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.getCountryCodeForRegionWithZeroPrefix
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import javax.inject.Inject

class HHOnBoardingMobileVM @Inject constructor(
    override val state: IHHOnBoardingMobile.State,
    override var validator: Validator?, private val repository: CustomersApi
) : DaggerBaseViewModel<IHHOnBoardingMobile.State>(), IHHOnBoardingMobile.ViewModel, IValidator {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
    }

    override fun handleOnClick(id: Int) {
    }

    override fun verifyHouseholdParentMobile(apiResponse: ((String?) -> Unit?)?) {
        launch {
            state.loading = true
            val request = VerifyHouseholdMobileRequest(
                countryCode = getCountryCodeForRegionWithZeroPrefix(state.countryCode?.value),
                mobileNo = state.phone?.value?.replace(" ", "")
            )
            when (val response =
                repository.verifyHouseholdParentMobile(request)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        SessionManager.user?.notificationStatuses = it
                        apiResponse?.invoke(it)
                        trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_PHONE_CORRECT.type)
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                   // apiResponse?.invoke(" ")
                    state.toast = response.error.message

                }
            }
        }

    }
}
//559987455