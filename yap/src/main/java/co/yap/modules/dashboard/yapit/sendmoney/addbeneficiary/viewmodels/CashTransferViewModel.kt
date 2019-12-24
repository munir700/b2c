package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.CashTransferState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.CashPayoutRequestDTO
import co.yap.networking.transactions.requestdtos.DomesticTransactionRequestDTO
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.requestdtos.UAEFTSTransactionRequestDTO
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.toast

class CashTransferViewModel(application: Application) :
    SendMoneyBaseViewModel<ICashTransfer.State>(application),
    ICashTransfer.ViewModel {

    private val transactionRepository: TransactionsRepository = TransactionsRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    private var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> =
        ArrayList()


    override val state: CashTransferState = CashTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    override var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList> =
        ArrayList()
    override val populateSpinnerData: MutableLiveData<ArrayList<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()
    override var receiverUUID: String = ""

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)

        transactionData.clear()
        state.currencyType = "AED"
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_display_text_title))
    }


    override fun handlePressOnView(id: Int) {
        if (state.checkValidity() == "") {
            if (!state.reasonTransferValue.equals("Select a Reason")) {
                createOtp(id = id)
            } else {
                toast(context, "Select a Reason")

            }

//            temporary comment this service for

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
                    state.loading = false
                    state.errorDescription = response.error.message
                    errorEvent.call()
                }
            }
            state.loading = false
        }
    }

    override fun cashPayoutTransferRequest(beneficiaryId: Int?) {
        launch {
            state.loading = true
            when (val response =
                transactionRepository.cashPayoutTransferRequest(
                    CashPayoutRequestDTO(
                        state.amount.toDouble(),
                        state.currencyType,
                        "8",
                        beneficiaryId,
                        state.noteValue
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    state.referenceNumber = response.data.data
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
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
                        state.reasonTransferCode,
                        state.reasonTransferValue,
                        state.noteValue
                    )

                )
                ) {
                is RetroApiResponse.Success -> {
                    state.referenceNumber = response.data.data
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
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
                        state.reasonTransferCode,
                        state.reasonTransferValue,
                        state.noteValue
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    state.referenceNumber = response.data.data
                    clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
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
                        listItemRemittanceFee = response.data.data!!.tierRateDTOList!!
                        state.listItemRemittanceFee = listItemRemittanceFee
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
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    /*
    * In this function get All List of reasons.
    * */

    override fun getTransactionInternationalReasonList() {
        launch {
            //            state.loading = true
            when (val response =
                transactionRepository.getTransactionInternationalReasonList(state.produceCode)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNullOrEmpty()) return@launch
                    response.data.data?.let {
                        transactionData.addAll(it.map { item ->
                            InternationalFundsTransferReasonList.ReasonList(
                                code = item.code ?: "",
                                reason = item.reason ?: ""
                            )
                        })
                    }
                    populateSpinnerData.value = transactionData
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.errorDescription = response.error.message
                    errorEvent.call()
                }
            }
            //state.loading = false
        }
    }


    /*
   * In this function get Remittance Transaction Fee.
   * */

    override fun getTransactionFeeInternational() {
        launch {
            val remittanceFeeRequestBody = RemittanceFeeRequest(state.beneficiaryCountry, "")
            when (val response =
                transactionRepository.getTransactionFeeWithProductCode(
                    state.produceCode,
                    remittanceFeeRequestBody
                )) {
                is RetroApiResponse.Success -> {

                    var totalAmount: Double
                    if (response.data.data?.feeType == "FLAT") {
                        val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                        val feeAmountVAT = response.data.data?.tierRateDTOList?.get(0)?.vatAmount
                        if (feeAmount != null) {
                            totalAmount = feeAmount + feeAmountVAT!!
                            state.transferFee =
                                getString(Strings.screen_international_funds_transfer_display_text_fee).format(
                                    "AED",
                                    Utils.getFormattedCurrency(totalAmount.toString())
                                )
                            state.transferFeeSpannable =
                                Utils.getSppnableStringForAmount(
                                    context,
                                    state.transferFee,
                                    "AED",
                                    Utils.getFormattedCurrencyWithoutComma(totalAmount.toString())
                                )
                        }

                    } else if (response.data.data?.feeType == "TIER") {
                        listItemRemittanceFee = response.data.data!!.tierRateDTOList!!
                        state.listItemRemittanceFee = listItemRemittanceFee
                    }
                    if (state.reasonsVisibility!!) {
                        getTransactionInternationalReasonList()
                    }
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.errorDescription = response.error.message
                }
            }
            // state.loading = false
        }
    }

}