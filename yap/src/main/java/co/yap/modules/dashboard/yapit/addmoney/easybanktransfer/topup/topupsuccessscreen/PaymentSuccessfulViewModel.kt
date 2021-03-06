package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupsuccessscreen

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.helpers.spannables.size
import co.yap.yapcore.managers.SessionManager

class PaymentSuccessfulViewModel(application: Application) :
    AddMoneyBaseViewModel<IPaymentSuccessful.State>(application),
    IPaymentSuccessful.ViewModel {
    override val state: IPaymentSuccessful.State = PaymentSuccessfulState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun setNewBalanceData(balance: String) {
        state.newBalanceText.value =
            context.resources.getText(
                getString(Strings.screen_lean_topup_payment_successful_new_balance_text),
                context.color(
                    R.color.colorPrimaryDark,
                    size(1.5f, balance.toFormattedCurrency())
                )
            )
    }

    override fun getAccountBalanceRequest() {
        launch {
            state.loading = true
            when (val response = SessionManager.repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    SessionManager.cardBalance.value =
                        (CardBalance(availableBalance = response.data.data?.availableBalance.toString()))
                    setNewBalanceData(SessionManager.cardBalance.value?.availableBalance.toString())
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                }
            }
        }
    }
}