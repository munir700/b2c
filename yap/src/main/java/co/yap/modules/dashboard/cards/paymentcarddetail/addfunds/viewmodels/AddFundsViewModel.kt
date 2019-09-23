package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import co.yap.modules.dashboard.constants.Constants
import co.yap.translation.Strings
import co.yap.yapcore.helpers.Utils

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
//        state.availableBalance = "1500"
        // state.availableBalanceText =
        //   " " + getString(Strings.common_text_currency_type) + " " + Utils.getFormattedCurrency(state.availableBalance)

        state.buttonTitle = getString(Strings.screen_add_funds_button_add)

        //success screen strings

        state.primaryCardUpdatedBalance =
            getString(Strings.screen_success_funds_transaction_display_text_primary_balance).format(
                state.currencyType,
                "800"
            )
        state.spareCardUpdatedBalance =
            getString(Strings.screen_success_funds_transaction_display_text_success_updated_prepaid_card_balance).format(
                state.currencyType,
                "500"
            )

    }

}