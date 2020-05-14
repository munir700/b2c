package co.yap.sendmoney.fundtransfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.SendMoneyTransferRequest
import co.yap.sendmoney.fundtransfer.interfaces.IInternationalTransactionConfirmation
import co.yap.sendmoney.fundtransfer.states.InternationalTransactionConfirmationState
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.extentions.parseToDouble

class InternationalTransactionConfirmationViewModel(application: Application) :
    BeneficiaryFundTransferBaseViewModel<IInternationalTransactionConfirmation.State>(application),
    IInternationalTransactionConfirmation.ViewModel {
    private var mTransactionsRepository: TransactionsRepository = TransactionsRepository
    override val state: InternationalTransactionConfirmationState =
        InternationalTransactionConfirmationState()
    override val isOtpRequired: MutableLiveData<Boolean> = MutableLiveData()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        getCutOffTimeConfiguration()
    }

    override fun handlePressOnButtonClick(id: Int) {
        clickEvent.postValue(id)
    }

    override fun rmtTransferRequest(beneficiaryId: String?) {
        launch {
            parentViewModel?.transferData?.value?.let {
                state.loading = true
                when (val response =
                    mTransactionsRepository.rmtTransferRequest(
                        SendMoneyTransferRequest(
                            amount = it.sourceAmount?.toDouble(),
                            currency = it.sourceCurrency,
                            purposeCode = parentViewModel?.selectedPop?.purposeCode,
                            beneficiaryId = parentViewModel?.beneficiary?.value?.id,
                            remarks = if (it.noteValue.isNullOrBlank()) null else it.noteValue,
                            purposeReason = parentViewModel?.selectedPop?.purposeDescription,
                            settlementAmount = it.destinationAmount.parseToDouble()
                        )
                    )
                    ) {
                    is RetroApiResponse.Success -> {
                        parentViewModel?.transferData?.value?.referenceNumber = response.data.data
                        clickEvent.postValue(Constants.ADD_SUCCESS)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                        state.loading = false
                    }
                }
                state.loading = false
            }
        }
    }

    override fun swiftTransferRequest(beneficiaryId: String?) {
        launch {
            parentViewModel?.transferData?.value?.let {
                state.loading = true
                when (val response =
                    mTransactionsRepository.swiftTransferRequest(
                        SendMoneyTransferRequest(
                            beneficiaryId = beneficiaryId?.toInt(),
                            amount = it.sourceAmount?.toDouble(),
                            settlementAmount = it.destinationAmount.parseToDouble(),
                            purposeCode = parentViewModel?.selectedPop?.purposeCode,
                            purposeReason = parentViewModel?.selectedPop?.purposeDescription,
                            remarks = if (it.noteValue.isNullOrBlank()) null else it.noteValue,
                            fxRate = it.rate
                        )
                    )
                    ) {
                    is RetroApiResponse.Success -> {
                        parentViewModel?.transferData?.value?.referenceNumber = response.data.data
                        clickEvent.postValue(Constants.ADD_SUCCESS)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                        state.loading = false
                    }
                }
                state.loading = false
            }
        }
    }

    override fun requestForTransfer() {
        if (isOtpRequired()) {
            isOtpRequired.value = true
        } else {
            proceedToTransferAmount()
        }
    }

    private fun isOtpRequired(): Boolean {
        parentViewModel?.transactionThreshold?.value?.let {
            it.totalDebitAmountRemittance?.let { totalSMConsumedAmount ->
                parentViewModel?.transferData?.value?.sourceAmount?.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimit?.minus(totalSMConsumedAmount)
                    return enteredAmount > (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

    override fun proceedToTransferAmount() {
        parentViewModel?.beneficiary?.value?.let { beneficiary ->
            beneficiary.beneficiaryType?.let { beneficiaryType ->
                if (beneficiaryType.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                        SendMoneyBeneficiaryType.RMT -> {
                            beneficiary.id?.let { beneficiaryId ->
                                rmtTransferRequest(beneficiaryId.toString())
                            }
                        }
                        SendMoneyBeneficiaryType.SWIFT -> {
                            beneficiary.id?.let { beneficiaryId ->
                                swiftTransferRequest(beneficiaryId.toString())
                            }
                        }
                        else -> {

                        }
                    }
            }

        }
    }


    override fun getCutOffTimeConfiguration() {
        parentViewModel?.beneficiary?.value?.run {
            beneficiaryType?.let { beneficiaryType ->
                if (beneficiaryType.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                        SendMoneyBeneficiaryType.SWIFT, SendMoneyBeneficiaryType.UAEFTS -> {
                            launch {
                                when (val response =
                                    mTransactionsRepository.getCutOffTimeConfiguration(
                                        getProductCode(),
                                        currency,
                                        parentViewModel?.transferData?.value?.sourceAmount,
                                        parentViewModel?.selectedPop?.cbwsi?:false
                                    )) {
                                    is RetroApiResponse.Success -> {
                                        response.data.data?.let {
                                            state.cutOffTimeMsg = it.errorMsg
                                        }

                                    }
                                    is RetroApiResponse.Error -> {
                                        state.toast = response.error.message
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun getProductCode(): String? {
        parentViewModel?.beneficiary?.value?.beneficiaryType?.let {
            when (SendMoneyBeneficiaryType.valueOf(it)) {
                SendMoneyBeneficiaryType.SWIFT -> {
                    return TransactionProductCode.SWIFT.pCode
                }
                SendMoneyBeneficiaryType.UAEFTS -> {
                    TransactionProductCode.UAEFTS.pCode
                }
                else -> null
            }
        }
        return null
    }
}