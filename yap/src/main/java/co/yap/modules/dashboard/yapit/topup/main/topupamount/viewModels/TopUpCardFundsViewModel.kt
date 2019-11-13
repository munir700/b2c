package co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.translation.Strings

class TopUpCardFundsViewModel(application: Application) : FundActionsViewModel(application) {

    override fun onCreate() {
        super.onCreate()
        state.toolBarHeader = getString(Strings.screen_topup_funds_tool_bar_header)
        state.enterAmountHeading = getString(Strings.screen_topup_funds_enter_amount_header)
        state.currencyType = getString(Strings.common_text_currency_type)
        //getFundTransferLimits(Constants.REMOVE_FUNDS_PRODUCT_CODE)
        //getFundTransferDenominations(Constants.REMOVE_FUNDS_PRODUCT_CODE)
        state.maxLimit = 20000.00
        state.minLimit = 0.01
        state.availableBalanceGuide =
            getString(Strings.screen_topup_funds_display_text_available_balance)
        state.buttonTitle = getString(Strings.screen_topup_funds_display_button_text)
        state.denominationFirstAmount = "+100"
        state.denominationSecondAmount = "+500"
        state.denominationThirdAmount = "+1000"
    }
}