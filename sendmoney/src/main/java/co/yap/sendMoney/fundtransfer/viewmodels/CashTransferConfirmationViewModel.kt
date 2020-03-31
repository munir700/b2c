package co.yap.sendMoney.fundtransfer.viewmodels

import android.app.Application
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.DomesticTransactionRequestDTO
import co.yap.networking.transactions.requestdtos.UAEFTSTransactionRequestDTO
import co.yap.sendMoney.fundtransfer.interfaces.ICashTransferConfirmation
import co.yap.sendMoney.fundtransfer.states.CashTransferConfirmationState
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode

class CashTransferConfirmationViewModel(application: Application) :
    BeneficiaryFundTransferBaseViewModel<ICashTransferConfirmation.State>(application),
    ICashTransferConfirmation.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override val repository: TransactionsRepository = TransactionsRepository
    override val state: CashTransferConfirmationState =
        CashTransferConfirmationState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.state?.leftIcon?.set(true)
        parentViewModel?.state?.rightIcon?.set(false)
        parentViewModel?.state?.toolBarTitle = "Confirm transfer"
        getCutOffTimeConfiguration()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun proceedToTransferAmount() {
        parentViewModel?.beneficiary?.value?.let { beneficiary ->
            beneficiary.id?.let { beneficiaryId ->
                if (beneficiary.beneficiaryType?.isNotEmpty() == true)
                    when (SendMoneyBeneficiaryType.valueOf(beneficiary.beneficiaryType ?: "")) {
                        SendMoneyBeneficiaryType.UAEFTS -> uaeftsTransferRequest(beneficiaryId.toString())
                        SendMoneyBeneficiaryType.DOMESTIC -> domesticTransferRequest(beneficiaryId.toString())
                        else -> state.toast = "Invalid Beneficiary Type"
                    }
            }
        }
    }


    override fun getCutOffTimeConfiguration() {
        launch {
            state.loading = true
            when (val response =
                repository.getCutOffTimeConfiguration(
                    getProductCode(),
                    "AED",
                    parentViewModel?.transferData?.value?.transferAmount
                )) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        parentViewModel?.transferData?.value?.cutOffTimeMsg = it.errorMsg
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
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
                        parentViewModel?.transferData?.value?.transferAmount?.toDoubleOrNull(),
                        0.0,
                        parentViewModel?.transferData?.value?.purposeCode,
                        parentViewModel?.transferData?.value?.transferReason,
                        if (parentViewModel?.transferData?.value?.noteValue.isNullOrBlank()) null else parentViewModel?.transferData?.value?.noteValue
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.transferData?.value?.referenceNumber = response.data.data
                    state.cutOffTimeMsg.set(response.data.data)
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun domesticTransferRequest(beneficiaryId: String?) {
        launch {
            state.loading = true
            when (val response =
                repository.domesticTransferRequest(
                    DomesticTransactionRequestDTO(
                        beneficiaryId,
                        parentViewModel?.transferData?.value?.transferAmount?.toDoubleOrNull(),
                        0.0,
                        parentViewModel?.transferData?.value?.purposeCode,
                        parentViewModel?.transferData?.value?.transferReason,
                        if (parentViewModel?.transferData?.value?.noteValue.isNullOrBlank()) null else parentViewModel?.transferData?.value?.noteValue
                    )

                )
                ) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.transferData?.value?.referenceNumber = response.data.data
                    state.cutOffTimeMsg.set(response.data.data)
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    private fun getProductCode(): String {
        return (when (parentViewModel?.beneficiary?.value?.beneficiaryType) {
            SendMoneyBeneficiaryType.UAEFTS.type -> TransactionProductCode.UAEFTS.pCode

            SendMoneyBeneficiaryType.DOMESTIC.type -> TransactionProductCode.DOMESTIC.pCode

            else -> ""
        })
    }

}