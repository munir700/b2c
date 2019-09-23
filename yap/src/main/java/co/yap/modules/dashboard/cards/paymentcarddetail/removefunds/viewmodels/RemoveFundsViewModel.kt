package co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.modules.dashboard.constants.Constants
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

class RemoveFundsViewModel(application: Application) : FundActionsViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun onCreate() {
        super.onCreate()
        state.toolBarHeader = getString(Strings.screen_remove_funds_display_text_remove_funds)
        state.enterAmountHeading = getString(Strings.screen_remove_funds_display_text_enter_amount)
        state.currencyType = getString(Strings.common_text_currency_type)
        getFundTransferDenominations(Constants.REMOVE_FUNDS_PRODUCT_CODE)
        state.minLimit = 1.00
        state.maxLimit = 1000.00
//        state.availableBalance = "1500"
        state.availableBalanceGuide =
            getString(Strings.screen_remove_funds_display_text_available_balance)
        state.buttonTitle = getString(Strings.screen_remove_funds_button_remove)
        // Success screen strings

        state.primaryCardUpdatedBalance =
            getString(Strings.screen_success_funds_transaction_display_text_primary_balance).format(
                state.currencyType,
                "800.00"
            )
        state.spareCardUpdatedBalance =
            getString(Strings.screen_success_funds_transaction_display_text_success_updated_prepaid_card_balance).format(
                state.currencyType,
                "500.00"
            )
    }

    override fun buttonClickEvent(id: Int) {
        if (state.checkValidity() == "") {
            clickEvent.postValue(id)
        } else {
            errorEvent.postValue(id)
        }
    }

}