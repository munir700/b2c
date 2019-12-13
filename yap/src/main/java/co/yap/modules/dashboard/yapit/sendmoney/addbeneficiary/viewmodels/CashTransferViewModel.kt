package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.CashTransferState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.Y2YFundsTransferRequest
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

class CashTransferViewModel(application: Application) :
    SendMoneyBaseViewModel<ICashTransfer.State>(application),
    ICashTransfer.ViewModel {
    override val state: CashTransferState = CashTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    private val repository: TransactionsRepository = TransactionsRepository
    override var receiverUUID: String = ""

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
        state.feeAmountString =
            getString(Strings.screen_cash_pickup_funds_display_text_fee).format("AED", "50.00")

        state.currencyType = "AED"
        state.feeAmountSpannableString = Utils.getSppnableStringForAmount(
            context,
            state.feeAmountString, state.currencyType, "50.00"
        )
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_display_text_title))
    }


    override fun handlePressOnView(id: Int) {
        if (state.checkValidity() == "") {
            // clickEvent.postValue(id)
//            temporary comment this service for
            clickEvent.postValue(id)

        } else {
            errorEvent.postValue(id)
        }
    }

    /*private fun y2yFundsTransferRequest(id: Int) {
        val y2yFundsTransfer = Y2YFundsTransferRequest(
            receiverUUID, state.fullName, state.amount, false, state.noteValue
        )
        launch {
            state.loading = true
            when (val response = repository.y2yFundsTransferRequest(y2yFundsTransfer)) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(id)
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.errorDescription = response.error.message
                    errorEvent.postValue(id)
                }
            }
            state.loading = false
        }
    }*/
}