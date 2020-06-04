package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.helpers.extentions.parseToDouble

class AddFundsViewModel(application: Application) : FundActionsViewModel(application) {

    override fun onCreate() {
        super.onCreate()
        getTransactionThresholds()
        state.currencyType = getString(Strings.common_text_currency_type)
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
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

    override fun buttonClickEvent(id: Int) {
        clickEvent.setValue(id)
    }
}