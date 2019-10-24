package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.states.Y2YFundsTransferState
import co.yap.modules.dashboard.yapit.y2ytransfer.viewmodels.Y2YBaseViewModel
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.Y2YFundsTransferRequest
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class Y2YFundsTransferViewModel(application: Application) :
    Y2YBaseViewModel<IY2YFundsTransfer.State>(application),
    IY2YFundsTransfer.ViewModel {
    override val state: Y2YFundsTransferState = Y2YFundsTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    private val repository: TransactionsRepository = TransactionsRepository
    override var receiverUUID: String = ""

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
    }

    override fun handlePressOnView(id: Int) {
        if (state.checkValidity() == "") {
            clickEvent.postValue(id)
//            temporary comment this service for
            //y2yFundsTransferRequest(id)

        } else {
            errorEvent.postValue(id)
        }
    }

    private fun y2yFundsTransferRequest(id: Int) {
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
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_display_text_title))
        setRightButtonVisibility(false)
        setLeftButtonVisibility(true)
    }
}