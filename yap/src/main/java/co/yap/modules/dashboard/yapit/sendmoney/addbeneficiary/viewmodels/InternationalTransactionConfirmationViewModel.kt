package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalTransactionConfirmation
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.InternationalTransactionConfirmationState
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RMTTransactionRequestDTO
import co.yap.networking.transactions.requestdtos.SwiftTransactionRequestDTO
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants

class InternationalTransactionConfirmationViewModel(application: Application) :
    BaseViewModel<IInternationalTransactionConfirmation.State>(application),
    IInternationalTransactionConfirmation.ViewModel {
    private var mTransactionsRepository: TransactionsRepository = TransactionsRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    override val state: InternationalTransactionConfirmationState =
        InternationalTransactionConfirmationState()
    override var otpAction: String? = null
    override val clickEvent: SingleClickEvent = SingleClickEvent()


    override fun handlePressOnButtonClick(id: Int) {
        clickEvent.postValue(id)
    }

    override fun rmtTransferRequest(beneficiaryId: String?) {
        launch {
            state.args?.let {
                state.loading = true
                when (val response =
                    mTransactionsRepository.rmtTransferRequest(
                        RMTTransactionRequestDTO(
                            it.fxRateAmount.toDouble(),
                            it.fromFxRateCurrency,
                            it.reasonTransferCode,
                            beneficiaryId,
                            it.transactionNote,
                            it.reasonTransferValue
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
                            it.rate
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
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(
                    createOtpGenericRequest = CreateOtpGenericRequest(
                        state.args?.otpAction ?: ""
                    )
                )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(CREATE_OTP_SUCCESS_EVENT)
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