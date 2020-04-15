package co.yap.sendmoney.fundtransfer.viewmodels

import android.app.Application
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.SendMoneyTransferRequest
import co.yap.sendmoney.fundtransfer.interfaces.ICashTransferConfirmation
import co.yap.sendmoney.fundtransfer.states.CashTransferConfirmationState
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.extentions.parseToDouble

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
                    productCode = getProductCode(),
                    currency = "AED",
                    amount = parentViewModel?.transferData?.value?.transferAmount,
                    isCbwsi = parentViewModel?.selectedPop?.cbwsi?:false
                )) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        state.cutOffTimeMsg.set(it.errorMsg)
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
                    SendMoneyTransferRequest(
                        beneficiaryId = beneficiaryId?.toInt(),
                        amount = parentViewModel?.transferData?.value?.transferAmount?.toDoubleOrNull(),
                        settlementAmount = 0.0,
                        purposeCode = parentViewModel?.selectedPop?.purposeCode,
                        purposeReason = parentViewModel?.selectedPop?.purposeDescription,
                        feeAmount = if (parentViewModel?.transferData?.value?.feeAmount.isNullOrBlank()) "0.0" else parentViewModel?.transferData?.value?.feeAmount,
                        vat = if (parentViewModel?.transferData?.value?.vat.isNullOrBlank()) "0.0" else parentViewModel?.transferData?.value?.vat,
                        totalCharges = parentViewModel?.transferData?.value?.transferFee,
                        totalAmount = parentViewModel?.transferData?.value?.transferAmount.parseToDouble().plus(
                            parentViewModel?.transferData?.value?.transferFee.parseToDouble()
                        ).toString(),
                        cbwsi = parentViewModel?.selectedPop?.cbwsi,
                        cbwsiFee = parentViewModel?.selectedPop?.cbwsiFee,
                        nonChargeable = parentViewModel?.selectedPop?.nonChargeable,
                        remarks = if (parentViewModel?.transferData?.value?.noteValue.isNullOrBlank()) null else parentViewModel?.transferData?.value?.noteValue

                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.transferData?.value?.referenceNumber = response.data.data
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
                    SendMoneyTransferRequest(
                        beneficiaryId = beneficiaryId?.toInt(),
                        amount = parentViewModel?.transferData?.value?.transferAmount?.toDoubleOrNull(),
                        settlementAmount = 0.0,
                        purposeCode = parentViewModel?.selectedPop?.purposeCode,
                        purposeReason = parentViewModel?.selectedPop?.purposeDescription,
                        remarks = if (parentViewModel?.transferData?.value?.noteValue.isNullOrBlank()) null else parentViewModel?.transferData?.value?.noteValue
                    )

                )
                ) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.transferData?.value?.referenceNumber = response.data.data
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