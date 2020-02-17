package co.yap.modules.dashboard.yapit.y2y.transfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.modules.dashboard.yapit.y2y.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2y.transfer.states.Y2YFundsTransferState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.Y2YFundsTransferRequest
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.Utils

class Y2YFundsTransferViewModel(application: Application) :
    Y2YBaseViewModel<IY2YFundsTransfer.State>(application),
    IY2YFundsTransfer.ViewModel {
    override val state: Y2YFundsTransferState = Y2YFundsTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    override val transactionThreshold: MutableLiveData<TransactionThresholdModel> =
        MutableLiveData()
    private val repository: TransactionsRepository = TransactionsRepository
    override var receiverUUID: String = ""
    override val transferFundSuccess: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
        state.currencyType = "AED"
        getTransactionThresholds()
    }

    override fun handlePressOnView(id: Int) {
        if (state.checkValidity() == "") {
            clickEvent.setValue(id)
        } else {
            errorEvent.postValue(id)
        }
    }

    override fun proceedToTransferAmount() {
        val y2yFundsTransfer = Y2YFundsTransferRequest(
            receiverUUID, state.fullName, state.amount, false, state.noteValue
        )
        launch {
            state.loading = true
            when (val response = repository.y2yFundsTransferRequest(y2yFundsTransfer)) {
                is RetroApiResponse.Success -> {
                    transferFundSuccess.value = true
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.errorDescription = response.error.message
                    errorEvent.call()
                }
            }
            state.loading = false
        }
    }

    override fun getTransactionFee() {
        launch {
            state.loading = true
            when (val response = repository.getTransactionFee(
                TransactionProductCode.Y2Y_TRANSFER.pCode
            )) {
                is RetroApiResponse.Success -> {
                    state.fee = Utils.getFormattedCurrency(response.data.data)
                    clickEvent.postValue(Constants.CARD_FEE)
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
                }
            }
            state.loading = false
        }
    }

    override fun getTransactionThresholds() {
        launch {
            when (val response = repository.getTransactionThresholds()) {
                is RetroApiResponse.Success -> {
                    transactionThreshold.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
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