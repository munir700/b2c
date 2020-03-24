package co.yap.sendMoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RMTTransactionRequestDTO
import co.yap.networking.transactions.requestdtos.SwiftTransactionRequestDTO
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.sendMoney.addbeneficiary.interfaces.IInternationalTransactionConfirmation
import co.yap.sendMoney.addbeneficiary.states.InternationalTransactionConfirmationState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryProductCode
import co.yap.yapcore.enums.SendMoneyBeneficiaryType

class InternationalTransactionConfirmationViewModel(application: Application) :
    BaseViewModel<IInternationalTransactionConfirmation.State>(application),
    IInternationalTransactionConfirmation.ViewModel {
    private var mTransactionsRepository: TransactionsRepository = TransactionsRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    override val state: InternationalTransactionConfirmationState =
        InternationalTransactionConfirmationState()
    override var otpAction: String? = null
    override var beneficiary: Beneficiary? = null
    override val transactionThreshold: MutableLiveData<TransactionThresholdModel> =
        MutableLiveData()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        getTransactionThresholds()
        getCutOffTimeConfiguration()
    }

    override fun handlePressOnButtonClick(id: Int) {
        clickEvent.postValue(id)
    }

    override fun rmtTransferRequest(beneficiaryId: String?) {
        launch {
            state.args.let {
                state.loading = true
                when (val response =
                    mTransactionsRepository.rmtTransferRequest(
                        RMTTransactionRequestDTO(
                            it?.fxRateAmount?.toDouble(),
                            it?.fromFxRateCurrency,
                            it?.reasonTransferCode,
                            beneficiaryId,
                            if (it?.transactionNote.isNullOrBlank()) null else it?.transactionNote,
                            it?.reasonTransferValue
                        )
                    )
                    ) {
                    is RetroApiResponse.Success -> {
                        state.referenceNumber = response.data.data
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
            state.args?.let {
                state.loading = true
                when (val response =
                    mTransactionsRepository.swiftTransferRequest(
                        SwiftTransactionRequestDTO(
                            beneficiaryId,
                            it.fxRateAmount.toDouble(),
                            0.0,
                            it.reasonTransferCode,
                            it.reasonTransferValue,
                            it.transactionNote,
                            it.srRate
                        )
                    )
                    ) {
                    is RetroApiResponse.Success -> {
                        state.referenceNumber = response.data.data
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

    override fun createOtp() {
        clickEvent.postValue(CREATE_OTP_SUCCESS_EVENT)
    }

    override fun requestForTransfer() {
        if (isOtpRequired()) {
            createOtp()
        } else {
            proceedToTransferAmount()
        }
    }

    private fun isOtpRequired(): Boolean {
        transactionThreshold.value?.let {
            it.totalDebitAmountRemittance?.let { totalSMConsumedAmount ->
                state.args?.fxRateAmount?.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimit?.minus(totalSMConsumedAmount)
                    return enteredAmount >= (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

    override fun proceedToTransferAmount() {
        beneficiary?.let { beneficiary ->
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

    override fun getTransactionThresholds() {
        launch {
            state.loading = true
            when (val response = mTransactionsRepository.getTransactionThresholds()) {
                is RetroApiResponse.Success -> {
                    transactionThreshold.value = response.data.data
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun getCutOffTimeConfiguration() {

        beneficiary?.run {
            beneficiaryType?.let { beneficiaryType ->
                if (beneficiaryType.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                        SendMoneyBeneficiaryType.SWIFT, SendMoneyBeneficiaryType.UAEFTS -> {
                            launch {
                                when (val response =
                                    mTransactionsRepository.getCutOffTimeConfiguration(
                                        getProductCode(),
                                        currency,
                                        state.args?.fxRateAmount
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
        beneficiary?.beneficiaryType?.let {
            when (SendMoneyBeneficiaryType.valueOf(it)) {
                SendMoneyBeneficiaryType.SWIFT -> {
                    return SendMoneyBeneficiaryProductCode.P011.name
                }
                SendMoneyBeneficiaryType.UAEFTS -> {
                    SendMoneyBeneficiaryProductCode.P010.name
                }
                else -> null
            }
        }
        return null
    }
}