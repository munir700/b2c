package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransferConfirmation
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.CashTransferConfirmationState
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.UAEFTSTransactionRequestDTO
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode

class CashTransferConfirmationViewModel(application: Application) :
    BaseViewModel<ICashTransferConfirmation.State>(application),
    ICashTransferConfirmation.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override val repository: TransactionsRepository = TransactionsRepository
    override val state: CashTransferConfirmationState = CashTransferConfirmationState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transactionThreshold: MutableLiveData<TransactionThresholdModel> =
        MutableLiveData()
    override var beneficiary: Beneficiary? = null
    override var reasonCode: String = ""
    override var reason: String = ""
    override var transferNote: String? = null

    override fun onCreate() {
        super.onCreate()
        getCutOffTimeConfiguration()
        getTransactionThresholds()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun proceedToTransferAmount() {
        beneficiary?.let { beneficiary ->
            beneficiary.id?.let { beneficiaryId ->
                uaeftsTransferRequest(beneficiaryId.toString())
            }
        }
    }


    override fun getTransactionThresholds() {
        launch {
            state.loading = true
            when (val response = repository.getTransactionThresholds()) {
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

    override fun getCutOffTimeConfiguration() {
        launch {
            when (val response =
                repository.getCutOffTimeConfiguration(
                    getProductCode(),
                    "AED",
                    state.enteredAmount.get()
                )) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        state.cutOffTimeMsg.set(it.errorMsg)
                    }

                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }

    }

    override fun uaeftsTransferRequest(beneficiaryId: String?) {
        launch {
            state.loading = true
            when (val response =
                repository.uaeftsTransferRequest(
                    UAEFTSTransactionRequestDTO(
                        beneficiaryId,
                        state.enteredAmount.get()?.toDouble(),
                        0.0,
                        reasonCode,
                        reason,
                        transferNote
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    state.referenceNumber.set(response.data.data)
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
//                    state.errorDescription = response.error.message
//                    errorEvent.call()
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    private fun getProductCode(): String {
        return (when (beneficiary?.beneficiaryType) {
            SendMoneyBeneficiaryType.UAEFTS.type -> TransactionProductCode.UAEFTS.pCode

            SendMoneyBeneficiaryType.DOMESTIC.type -> TransactionProductCode.DOMESTIC.pCode

            else -> ""
        })
    }

}