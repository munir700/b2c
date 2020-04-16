package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants

class AddFundsViewModel(application: Application) : FundActionsViewModel(application) {

    override fun onCreate() {
        super.onCreate()
        //TODO: call api for threshold here and save that response in viewmodel just like y2y

        state.toolBarHeader = getString(Strings.screen_add_funds_display_text_add_funds)
        state.enterAmountHeading = getString(Strings.screen_add_funds_display_text_enter_amount)
        state.currencyType = getString(Strings.common_text_currency_type)
        getFundTransferLimits(Constants.SUPP_CARD)
        getFundTransferDenominations(Constants.SUPP_CARD)
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
        state.buttonTitle = getString(Strings.screen_add_funds_button_add)
    }

}