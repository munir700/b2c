package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import co.yap.modules.others.helper.Constants
import co.yap.translation.Strings

class AddFundsViewModel(application: Application) : FundActionsViewModel(application) {


    override fun onCreate() {
        super.onCreate()
        state.toolBarHeader = getString(Strings.screen_add_funds_display_text_add_funds)
        state.enterAmountHeading = getString(Strings.screen_add_funds_display_text_enter_amount)
        state.currencyType = getString(Strings.common_text_currency_type)
        getFundTransferLimits(Constants.ADD_FUNDS_PRODUCT_CODE)
        getFundTransferDenominations(Constants.ADD_FUNDS_PRODUCT_CODE)
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
        state.buttonTitle = getString(Strings.screen_add_funds_button_add)
    }

}