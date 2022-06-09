package co.yap.modules.dashboard.store.household.paymentconfirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.enums.PackageType
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HouseHoldConfirmPaymentVM @Inject constructor(override var state: HouseHoldConfirmPaymentState) :
    HiltBaseViewModel<IHouseHoldConfirmPayment.State>(), IHouseHoldConfirmPayment.ViewModel {

    override var repository: CustomersApi = CustomersRepository

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.onBoardRequest?.value = it.getParcelable(HouseholdOnboardRequest::class.java.name)
            state.plansList?.value = it.getParcelableArrayList(HouseHoldPlan::class.java.name)
            state.selectedPlan?.value =
                state.plansList?.value?.get(it.getInt(Constants.POSITION, 0))
        }
    }

    override fun addHouseholdUser(apiResponse: ((Boolean?) -> Unit?)?) {
        launch {
            state.loading = true
            with(state.onBoardRequest?.value) {
                this?.feeFrequency = state.selectedPlan?.value?.type?.toUpperCase(Locale.US)
                if (this?.countryCode?.contains("00") == false)
                    this.countryCode = "00${this.countryCode?.replace("+", "")}"
            }
            when (val response = repository.onboardHousehold(state.onBoardRequest?.value)) {
                is RetroApiResponse.Success -> {
                    trackEvent(HHSubscriptionEvents.HH_PLAN_CONFIRM.type)
                    trackEventWithAttributes(SessionManager.user, isMainUser = true)
                    if (SessionManager.user?.accountType == AccountType.B2C_HOUSEHOLD.name) {
                        if (state.selectedPlan?.value?.type == PackageType.MONTHLY.type) {
                            trackEventWithAttributes(
                                SessionManager.user,
                                accountActiveMonthly = true
                            )
                        }
                    }
                    state.onBoardRequest?.value?.tempPassCode =
                        response.data.data?.passcode
                    apiResponse?.invoke(true)
                }

                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }
}
