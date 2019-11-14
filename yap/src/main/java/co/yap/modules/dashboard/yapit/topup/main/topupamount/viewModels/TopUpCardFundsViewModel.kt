package co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.translation.Strings
import co.yap.yapcore.helpers.Utils

class TopUpCardFundsViewModel(application: Application) : FundActionsViewModel(application) {
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override fun onCreate() {
        super.onCreate()
        state.toolBarHeader = getString(Strings.screen_topup_transfer_display_text_screen_title)
        state.enterAmountHeading = getString(Strings.screen_topup_transfer_display_text_amount_title)
        state.currencyType = getString(Strings.common_text_currency_type)
        //getFundTransferLimits(Constants.REMOVE_FUNDS_PRODUCT_CODE)
        //getFundTransferDenominations(Constants.REMOVE_FUNDS_PRODUCT_CODE)
        getTransactionFee()
        state.maxLimit = 20000.00
        state.minLimit = 0.01
        state.availableBalance = "500"
        state.availableBalanceGuide =
            getString(Strings.screen_topup_transfer_display_text_available_balance)
                .format(
                    state.currencyType,
                    Utils.getFormattedCurrency(state.availableBalance)
                )
        state.buttonTitle = getString(Strings.screen_topup_funds_display_button_text)
        state.denominationFirstAmount = "+100"
        state.denominationSecondAmount = "+500"
        state.denominationThirdAmount = "+1000"
        state.transactionFee="0.0"
        if (state.transactionFee.toDouble()==0.0){
            state.transactionFee = "No Fee"
        }

    }

    fun getTransactionFee(){
        launch {
            state.loading = true
            when (val response = transactionsRepository.getTransactionFee(""
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.call()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }
}