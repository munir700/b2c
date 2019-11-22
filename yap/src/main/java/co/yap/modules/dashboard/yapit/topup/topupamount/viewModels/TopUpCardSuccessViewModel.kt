package co.yap.modules.dashboard.yapit.topup.topupamount.viewModels

import android.app.Application
import co.yap.modules.dashboard.yapit.topup.topupamount.interfaces.ITopUpCardSuccess
import co.yap.modules.dashboard.yapit.topup.topupamount.states.TopUpCardSuccessState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils


class TopUpCardSuccessViewModel(application: Application) :
    BaseViewModel<ITopUpCardSuccess.State>(application), ITopUpCardSuccess.ViewModel {

    override val state: TopUpCardSuccessState =
        TopUpCardSuccessState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.toolBarTitle = getString(Strings.screen_topup_success_display_text_title)
        state.buttonTitle =
            getString(Strings.screen_topup_success_display_text_dashboard_action_button_title)
        state.topUpSuccess =
            getString(Strings.screen_topup_success_display_text_success_transaction_message)

        state.topUpSuccess =
            getString(Strings.screen_topup_success_display_text_success_transaction_message).format(
                state.currencyType,
                Utils.getFormattedCurrency(state.amount)
            )

    }


    override fun buttonClickEvent(id: Int) {
        clickEvent.postValue(id)
    }
}