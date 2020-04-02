package co.yap.modules.dashboard.yapit.y2y.transfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.modules.dashboard.yapit.y2y.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2y.transfer.states.Y2YFundsTransferState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.requestdtos.Y2YFundsTransferRequest
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionProductCode

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
    override var enteredAmount: MutableLiveData<String> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
        state.currencyType = "AED"
        getTransactionThresholds()
        getTransactionLimits()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun proceedToTransferAmount() {
        val y2yFundsTransfer = Y2YFundsTransferRequest(
            receiverUUID, state.fullName, enteredAmount.value, false, if(state.noteValue.isBlank()) null else state.noteValue
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
                    parentViewModel?.errorEvent?.value = state.errorDescription
                    //errorEvent.call()
                }
            }
            state.loading = false
        }
    }

    override fun getTransactionFee() {
        launch {
            state.loading = true
            when (val response = repository.getTransactionFeeWithProductCode(
                TransactionProductCode.Y2Y_TRANSFER.pCode, RemittanceFeeRequest()
            )) {
                is RetroApiResponse.Success -> {
                    if (response.data.data?.feeType == Constants.FEE_TYPE_FLAT) {
                        val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                        val VATAmount = response.data.data?.tierRateDTOList?.get(0)?.vatAmount

                        clickEvent.postValue(1122)
                    }
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

    override fun getTransactionLimits() {
        launch {
            when (val response =
                repository.getFundTransferLimits(TransactionProductCode.Y2Y_TRANSFER.pCode)) {
                is RetroApiResponse.Success -> {
                    state.maxLimit = response.data.data?.maxLimit?.toDouble() ?: 0.00
                    state.minLimit = response.data.data?.minLimit?.toDouble() ?: 0.00
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