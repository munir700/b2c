package co.yap.modules.dashboard.store.household.paymentconfirmation

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.enums.PackageType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.MyUserManager
import java.util.*
import javax.inject.Inject

class HouseHoldConfirmPaymentVM @Inject constructor(override var state: IHouseHoldConfirmPayment.State) :
    DaggerBaseViewModel<IHouseHoldConfirmPayment.State>(), IHouseHoldConfirmPayment.ViewModel {

    override var repository: CustomersApi = CustomersRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
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
            state.onBoardRequest?.value?.feeFrequency =
                state.selectedPlan?.value?.type?.toUpperCase(Locale.US)
            when (val response = repository.onboardHousehold(state.onBoardRequest?.value)) {
                is RetroApiResponse.Success -> {
                    trackEvent(HHSubscriptionEvents.HH_PLAN_CONFIRM.type)
                    trackEventWithAttributes(MyUserManager.user, isMainUser = true)
                    if(MyUserManager.user?.accountType == AccountType.B2C_HOUSEHOLD.name){
                        if(state.selectedPlan?.value?.type == PackageType.MONTHLY.type) {
                            trackEventWithAttributes(
                                MyUserManager.user,
                                accountActiveMonthly = true
                            )
                        }
                    }
                    state.onBoardRequest?.value?.tempPassCode =
                        response.data.data?.passcode ?: "0000"
                    apiResponse?.invoke(true)
                }

                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }
}
