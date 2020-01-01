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
    }


    private fun setAvailableBalance() {
        state.availableBalance.set(
            getString(Strings.screen_topup_transfer_display_text_available_balance)
                .format(
                    "AED",
                    Utils.getFormattedCurrency(MyUserManager.cardBalance.value?.availableBalance.toString())
                )
        )
    }

}