package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.CashTransferState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.*
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils

class CashTransferViewModel(application: Application) :
    SendMoneyBaseViewModel<ICashTransfer.State>(application),
    ICashTransfer.ViewModel {
    override val state: CashTransferState = CashTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    private val transactionRepository: TransactionsRepository = TransactionsRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    override var receiverUUID: String = ""

    override fun onCreate() {
        super.onCreate()
        // getTransactionFeeForCashPayout()
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)


        state.currencyType = "AED"
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_display_text_title))
    }


    override fun handlePressOnView(id: Int) {
        if (state.checkValidity() == "") {
//            temporary comment this service for
            createOtp(id = id)
        } else {
            errorEvent.postValue(id)
        }
    }

    private fun createOtp(id: Int = 0) {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(
                    createOtpGenericRequest = CreateOtpGenericRequest(
                        Constants.BENEFICIARY_CASH_TRANSFER
                    )
                )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(id)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun cashPayoutTransferRequest(beneficiaryId: String?) {
        launch {
            state.loading = true
            when (val response =
                transactionRepository.cashPayoutTransferRequest(
                    CashPayoutRequestDTO(
                        "other",
                        beneficiaryId, state.amount.toDouble(), state.currencyType,
                        state.noteValue
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                  //  clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                    state.errorDescription = response.error.message
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
                transactionRepository.domesticTransferRequest(
                    DomesticTransactionRequestDTO(
                        beneficiaryId,
                        state.amount.toDouble(),
                        0.0,
                        "57",
                        "iueieieieiei",
                        state.noteValue
                    )

                )
                ) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                    state.errorDescription = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun uaeftsTransferRequest(beneficiaryId: String?) {

        launch {
            state.loading = true
            when (val response =
                transactionRepository.uaeftsTransferRequest(
                    UAEFTSTransactionRequestDTO(
                        beneficiaryId,
                        state.amount.toDouble(),
                        0.0,
                        "51",
                        "dsdsdsds",
                        state.noteValue,
                        ""
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                  //  clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                    state.errorDescription = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }


    }

    override fun getTransactionFeeForCashPayout(productCode: String?) {
        launch {
            state.loading = true
            when (val response =
                transactionRepository.getTransactionFeeWithProductCode(
                    productCode,
                    RemittanceFeeRequest(state.beneficiaryCountry, "")
                )
                ) {
                is RetroApiResponse.Success -> {

                    var totalAmount = 0.0
                    if (response.data.data?.feeType == "FLAT") {
                        val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                        val feeAmountVAT = response.data.data?.tierRateDTOList?.get(0)?.vatAmount
                        if (feeAmount != null) {
                            totalAmount = feeAmount + feeAmountVAT!!
                        }

                    } else if (response.data.data?.feeType == "TIER") {

                        println(response.data.data)
                        println(response.data.data)
                        val list = response.data.data!!.tierRateDTOList

                    } else {
                        totalAmount = 0.0
                    }

                    state.feeAmountString =
                        getString(Strings.screen_cash_pickup_funds_display_text_fee).format(
                            state.currencyType,
                            Utils.getFormattedCurrency(totalAmount.toString())
                        )
                    state.feeAmountSpannableString = Utils.getSppnableStringForAmount(
                        context,
                        state.feeAmountString,
                        state.currencyType,
                        Utils.getFormattedCurrencyWithoutComma(totalAmount.toString())
                    )

                    //clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    //clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                    state.errorDescription = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }
}