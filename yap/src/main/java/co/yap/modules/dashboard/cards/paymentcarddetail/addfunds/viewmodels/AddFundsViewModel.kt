package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants

class AddFundsViewModel(application: Application) : FundActionsViewModel(application) {

    override fun onCreate() {
        super.onCreate()

        getTransactionThresholds()

        state.toolBarHeader = getString(Strings.screen_add_funds_display_text_add_funds)
        state.enterAmountHeading = getString(Strings.screen_add_funds_display_text_enter_amount)
        state.currencyType = getString(Strings.common_text_currency_type)
        getFundTransferLimits(Constants.SUPP_CARD)
        getFundTransferDenominations(Constants.SUPP_CARD)
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
        state.buttonTitle = getString(Strings.screen_add_funds_button_add)
    }

    override fun getTransactionThresholds() {
        launch {
            when (val response = transactionsRepository.getTransactionThresholds()) {
                is RetroApiResponse.Success -> {
                    transactionThreshold.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }
}