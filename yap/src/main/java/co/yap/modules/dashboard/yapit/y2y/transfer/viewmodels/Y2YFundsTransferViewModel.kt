package co.yap.modules.dashboard.yapit.y2y.transfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.modules.dashboard.yapit.y2y.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2y.transfer.states.Y2YFundsTransferState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.Y2YFundsTransferRequest
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.FeeType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.extentions.parseToDouble

class Y2YFundsTransferViewModel(application: Application) :
    Y2YBaseViewModel<IY2YFundsTransfer.State>(application),
    IY2YFundsTransfer.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: Y2YFundsTransferState = Y2YFundsTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val transactionThreshold: MutableLiveData<TransactionThresholdModel> =
        MutableLiveData()
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val repository: CustomersRepository = CustomersRepository
    override var receiverUUID: String = ""
//    override val transferFundSuccess: MutableLiveData<Boolean> = MutableLiveData(false)

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

    override fun proceedToTransferAmount(success: () -> Unit) {
        val y2yFundsTransfer = Y2YFundsTransferRequest(
            receiverUUID,
            state.fullName,
            state.amount,
            false,
            if (state.noteValue.isBlank()) null else state.noteValue
        )
        launch {
            state.loading = true
            when (val response = transactionsRepository.y2yFundsTransferRequest(y2yFundsTransfer)) {
                is RetroApiResponse.Success -> {
                    success.invoke()
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.errorDescription = response.error.message
                    parentViewModel?.errorEvent?.value = state.errorDescription
                }
            }
            state.loading = false
        }
    }

    override fun checkCoolingPeriodRequest(
        beneficiaryId: String?,
        beneficiaryCreationDate: String?,
        beneficiaryName: String?,
        amount: String?,
        success: () -> Unit
    ) {
        /*val coolingPeriodRequestData = CoolingPeriodRequest(
            beneficiaryId = receiverUUID,
            beneficiaryCreationDate = state.fullName,
            beneficiaryName = state.amount,
            amount = "500"
        )*/
        launch {
            state.loading = true
            when (val response =
                transactionsRepository.checkCoolingPeriodRequest(
                    beneficiaryId,
                    beneficiaryCreationDate,
                    beneficiaryName,
                    amount
                )) {
                is RetroApiResponse.Success -> {
                    success.invoke()
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.errorDescription = response.error.message
                    parentViewModel?.errorEvent?.value = state.errorDescription
                }
            }
            state.loading = false
        }
    }

    override fun getTransactionThresholds() {
        launch {
            state.loading = true
            when (val response = transactionsRepository.getTransactionThresholds()) {
                is RetroApiResponse.Success -> {
                    transactionThreshold.value = response.data.data
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun getTransactionLimits() {
        launch {
            when (val response =
                transactionsRepository.getFundTransferLimits(TransactionProductCode.Y2Y_TRANSFER.pCode)) {
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

    fun getTotalAmountWithFee(): Double {
        return (when (feeType) {
            FeeType.TIER.name -> {
                val transferFee = getFeeFromTier(state.amount)
                state.amount.toDoubleOrNull() ?: 0.0.plus(
                    transferFee?.toDoubleOrNull() ?: 0.0
                )
            }
            FeeType.FLAT.name -> {
                state.amount.parseToDouble().plus(getFlatFee(state.amount).parseToDouble())
            }
            else -> {
                state.amount.parseToDouble()
            }
        })
    }


    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_display_text_title))
        setRightButtonVisibility(false)
        setLeftButtonVisibility(true)
    }
}