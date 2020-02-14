package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.CashTransferState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.CashPayoutRequestDTO
import co.yap.networking.transactions.requestdtos.DomesticTransactionRequestDTO
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.requestdtos.UAEFTSTransactionRequestDTO
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.TransactionThresholdModel
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import kotlin.math.abs

class CashTransferViewModel(application: Application) :
    SendMoneyBaseViewModel<ICashTransfer.State>(application),
    ICashTransfer.ViewModel {

    private val transactionRepository: TransactionsRepository = TransactionsRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    private var listItemRemittanceFee: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> =
        ArrayList()
    private val customersRepository: CustomersRepository = CustomersRepository

    override val state: CashTransferState = CashTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    override var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList> =
        ArrayList()
    override val populateSpinnerData: MutableLiveData<ArrayList<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()
    override var receiverUUID: String = ""
    override var transactionThreshold: MutableLiveData<TransactionThresholdModel> =
        MutableLiveData()
    override var reasonPosition: Int = 0

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceGuide =
            getString(Strings.screen_add_funds_display_text_available_balance)
        state.currencyType = "AED"
        state.setSpannableFee("0.0")
        getTransactionThresholds()
        getCutOffTimeConfiguration()
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_display_text_title))
    }

    override fun handlePressOnView(id: Int) {
        if (R.id.btnConfirm == id) {
            if (state.checkValidity() == "") {
                if (!state.reasonTransferValue.equals("Select a Reason")) {
                    when {
                        isDailyLimitReached() -> {
                            errorEvent.call()
                        }
                        isOtpRequired() -> {
                            createOtp(id = id)
                        }
                        else -> {
                            proceedToTransferAmount()
                        }
                    }
                } else {
                    toast(
                        context,
                        "Select a Reason"
                    )
                }
            } else {
                errorEvent.setValue(id)
            }
        } else {
            clickEvent.setValue(id)
        }
    }

    private fun isDailyLimitReached(): Boolean {
        transactionThreshold.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    state.amount.toDoubleOrNull()?.let { enteredAmount ->
                        val remainingDailyLimit = abs(dailyLimit - totalConsumedAmount)
                        state.errorDescription =
                            getString(Strings.common_display_text_daily_limit_error).format(
                                dailyLimit,
                                remainingDailyLimit
                            )
                        return enteredAmount > remainingDailyLimit

                    } ?: return false
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun isOtpRequired(): Boolean {
        transactionThreshold.value?.let {
            it.totalDebitAmountRemittance?.let { totalSMConsumedAmount ->
                state.amount.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimit ?: 0.0 - totalSMConsumedAmount
                    return enteredAmount > remainingOtpLimit
                } ?: return false
            } ?: return false
        } ?: return false
    }

    override fun proceedToTransferAmount() {
        state.beneficiary?.let { beneficiary ->
            beneficiary.beneficiaryType?.let {
                if (beneficiary.beneficiaryType?.isNotEmpty() == true)
                    when (SendMoneyBeneficiaryType.valueOf(beneficiary.beneficiaryType ?: "")) {
                        SendMoneyBeneficiaryType.CASHPAYOUT -> {
                            beneficiary.id?.let { beneficiaryId ->
                                cashPayoutTransferRequest(beneficiaryId)
                            }
                        }
                        //Rak to Rak(yap to rak(Internal transfer))
                        SendMoneyBeneficiaryType.DOMESTIC -> {
                            beneficiary.id?.let { beneficiaryId ->
                                domesticTransferRequest(beneficiaryId.toString())
                            }
                        }
                        //UAE non RAK(within UAE(External transfer))
                        SendMoneyBeneficiaryType.UAEFTS -> {
                            beneficiary.id?.let { beneficiaryId ->
                                uaeftsTransferRequest(beneficiaryId.toString())
                            }
                        }
                        else -> {

                        }
                    }
            }

        }
    }

    private fun createOtp(id: Int = 0) {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(
                    createOtpGenericRequest = CreateOtpGenericRequest(
                        state.otpAction ?: ""
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

    override fun getCashTransferReasonList() {
        launch {
            transactionData.clear()
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
                    state.feeType = response.data.data?.feeType
                    var totalAmount = 0.0
                    if (state.feeType == Constants.FEE_TYPE_FLAT) {
                        val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                        val feeAmountVAT =
                            response.data.data?.tierRateDTOList?.get(0)?.vatAmount
                        if (feeAmount != null) {
                            totalAmount = feeAmount + feeAmountVAT!!
                        }

                    } else if (state.feeType == Constants.FEE_TYPE_TIER) {
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
                    if (state.reasonsVisibility == true) {
                        getCashTransferReasonList()
                    }
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

    override fun getMoneyTransferLimits(productCode: String?) {
        launch {
            when (val response = transactionRepository.getFundTransferLimits(productCode)) {
                is RetroApiResponse.Success -> {
                    state.maxLimit = response.data.data?.maxLimit?.toDouble() ?: 0.00
                    state.minLimit = response.data.data?.minLimit?.toDouble() ?: 0.00
                    getCountryLimit()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }

    }

    override fun getCountryLimit() {
        launch {
            when (val response = customersRepository.getCountryTransactionLimits(
                state.beneficiary?.country ?: "",
                state.beneficiary?.currency ?: ""
            )) {
                is RetroApiResponse.Success -> {
                    setMaxMinLimits(response.data.data?.toDoubleOrNull())
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun getTransactionThresholds() {
        launch {
            when (val response = transactionRepository.getTransactionThresholds()) {
                is RetroApiResponse.Success -> {
                    transactionThreshold.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

    private fun setMaxMinLimits(limit: Double?) {
        limit?.let {
            if (it > 0.0) {
                state.maxLimit = it
                if (it < state.minLimit) {
                    state.minLimit = 1.0
                }
            }
        }
    }

    override fun getCutOffTimeConfiguration() {

        state.beneficiary?.run {
            beneficiaryType?.let { beneficiaryType ->
                if (beneficiaryType.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                        SendMoneyBeneficiaryType.SWIFT, SendMoneyBeneficiaryType.UAEFTS -> {
                            launch {
                                when (val response =
                                    transactionRepository.getCutOffTimeConfiguration(
                                        state.produceCode,
                                        currency
                                    )) {
                                    is RetroApiResponse.Success -> {
                                        response.data.data?.let {
                                            state.cutOffTimeMsg =  it.errorMsg
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
}