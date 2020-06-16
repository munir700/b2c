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
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class HouseHoldConfirmPaymentVM @Inject constructor(override var state: IHouseHoldConfirmPayment.State) :
    DaggerBaseViewModel<IHouseHoldConfirmPayment.State>(), IHouseHoldConfirmPayment.ViewModel {

    override var repository: CustomersApi = CustomersRepository
    override var selectedPlanType: HouseHoldPlan? = HouseHoldPlan()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var onBoardUserSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    override var plansList: MutableLiveData<ArrayList<HouseHoldPlan>> = MutableLiveData(ArrayList())
    override var firstName: String = ""
    override var lastName: String = ""
    override var username: String = ""
    override var userMobileNo: String = ""
    override var countryCode: String = "00971"
    override var tempPasscode: String = "0000"
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        /*setToolBarTitle("Confirm payment")
    toggleToolBarVisibility(true)*/
//        plansList.value = parentViewModel?.plansList // This is the value that will be pass from previous fragment
        setAvailableBalance()
        initSelectedPlan()
    }

    private fun initSelectedPlan() {
        state.selectedPlanFee.set(selectedPlanType?.amount)
        state.selectedCardPlan.set(selectedPlanType?.type + " | " + state.selectedPlanFee.get())
        selectedPlanType?.discount?.let {
            if (it != 0) {
                state.selectedPlanSaving.set("Your saving $it%!")
            }
        }
    }

    private fun setAvailableBalance() {
        val balanceString = getString(Strings.screen_topup_transfer_display_text_available_balance)
            .format(
                state.currencyType.get().toString(),
                MyUserManager.cardBalance.value?.availableBalance.toString().toFormattedCurrency()
            )
        state.availableBalance.set(
            Utils.getSppnableStringForAmount(
                context,
                balanceString,
                state.currencyType.get().toString(),
                Utils.getFormattedCurrencyWithoutComma(MyUserManager.cardBalance.value?.availableBalance.toString())
            )
        )
    }

    override fun addHouseholdUser() {
        launch {
            state.loading = true
            when (val response = repository.onboardHousehold(getOnboardRequest())) {
                is RetroApiResponse.Success -> {
                    tempPasscode = response.data.data?.passcode ?: "0000"
                    onBoardUserSuccess.value = true
                }

                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    private fun getOnboardRequest(): HouseholdOnboardRequest {
        return HouseholdOnboardRequest(
            firstName = firstName,
            lastName = lastName,
            countryCode = "00$countryCode",
            mobileNo = userMobileNo.replace(" ", ""),
            accountType = AccountType.B2C_HOUSEHOLD.name,
            feeFrequency = selectedPlanType?.type.toString().toUpperCase()
        )
    }

}
