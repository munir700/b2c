package co.yap.modules.dashboard.store.household.onboarding.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldConfirmPayment
import co.yap.modules.dashboard.store.household.onboarding.states.HouseHoldConfirmPaymentState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager

class HouseHoldConfirmPaymentViewModel(application: Application) :
    BaseOnboardingViewModel<IHouseHoldConfirmPayment.State>(application),
    IHouseHoldConfirmPayment.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IHouseHoldConfirmPayment.State = HouseHoldConfirmPaymentState()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("Confirm payment")
        toggleToolBarVisibility(true)
        setAvailableBalance()
        initSelectedPlan()
    }

    private fun initSelectedPlan() {
        state.selectedPlanFee.set(state.currencyType.get() + " " + parentViewModel?.selectedPlanType?.amount)
        state.selectedCardPlan.set(parentViewModel?.selectedPlanType?.type + " | " + state.selectedPlanFee.get())
        parentViewModel?.selectedPlanType?.discount?.let {
            if (it != 0.0) {
                state.selectedPlanSaving.set("Your saving $it%!")
            }
        }
    }

    private fun setAvailableBalance() {
        val balanceString = getString(Strings.screen_topup_transfer_display_text_available_balance)
            .format(
                state.currencyType.get().toString(),
                Utils.getFormattedCurrency(MyUserManager.cardBalance.value?.availableBalance.toString())
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
}