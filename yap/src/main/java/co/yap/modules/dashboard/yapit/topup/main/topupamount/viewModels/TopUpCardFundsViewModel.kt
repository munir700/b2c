package co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager

class TopUpCardFundsViewModel(application: Application) : FundActionsViewModel(application) {
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override fun onCreate() {
        super.onCreate()
        state.toolBarHeader = getString(Strings.screen_topup_transfer_display_text_screen_title)
        state.enterAmountHeading = getString(Strings.screen_topup_transfer_display_text_amount_title)
        state.currencyType = getString(Strings.common_text_currency_type)
        getFundTransferLimits(co.yap.modules.others.helper.Constants.ADD_FUNDS_PRODUCT_CODE)
        getFundTransferDenominations(co.yap.modules.others.helper.Constants.ADD_FUNDS_PRODUCT_CODE)
        getTransactionFee()
        state.availableBalanceGuide =
            getString(Strings.screen_topup_transfer_display_text_available_balance)
                .format(
                    state.currencyType,
                    Utils.getFormattedCurrency(MyUserManager.cardBalance.value?.availableBalance.toString())
                )
        state.buttonTitle = getString(Strings.screen_topup_funds_display_button_text)
    }

    private fun getTransactionFee(){
        launch {
            state.loading = true
            when (val response = transactionsRepository.getTransactionFee(Constants.TOP_UP
            )) {
                is RetroApiResponse.Success -> {
                    state.transactionFee=response.data.data
                    if (state.transactionFee.toDouble()==0.0){
                        state.transactionFee = getString(Strings.screen_topup_transfer_display_text_transaction_no_fee)
                    }
                    clickEvent.postValue(Constants.CARD_FEE)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }
}