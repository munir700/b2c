package co.yap.sendMoney.fundtransfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.CashPayoutRequestDTO
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.sendMoney.fundtransfer.interfaces.ICashTransfer
import co.yap.sendMoney.fundtransfer.states.CashTransferState
import co.yap.sendmoney.R
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.FeeType
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager

class CashTransferViewModel(application: Application) :
    BeneficiaryFundTransferBaseViewModel<ICashTransfer.State>(application),
    ICashTransfer.ViewModel {

    private val transactionRepository: TransactionsRepository = TransactionsRepository
    private val customersRepository: CustomersRepository = CustomersRepository
    override val state: CashTransferState =
        CashTransferState(application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    override var transactionData: MutableLiveData<ArrayList<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()
    override var receiverUUID: String = ""
    override var purposeOfPaymentList: MutableLiveData<ArrayList<PurposeOfPayment>> =
        MutableLiveData()
    override var feeType: String = ""
    override var feeTiers: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> = arrayListOf()
    override var isAPIFailed: MutableLiveData<Boolean> = MutableLiveData(false)
    override val updatedFee: MutableLiveData<String> = MutableLiveData("0.0")
    var purposeCategories: Map<String?, List<PurposeOfPayment>>? = HashMap()
    override var reasonPosition: Int = 0

    override fun onCreate() {
        super.onCreate()
        getTransactionThresholds()
        state.availableBalanceString = context.resources.getText(
            getString(Strings.screen_cash_transfer_display_text_available_balance),
            context.color(
                R.color.colorPrimaryDark,
                "${"AED"} ${MyUserManager.cardBalance.value?.availableBalance?.toFormattedCurrency()}"
            )
        )
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_y2y_funds_transfer_display_text_title))
        setToolbarData()
    }

    private fun setToolbarData() {
        parentViewModel?.state?.leftIcon?.set(false)
        parentViewModel?.state?.rightIcon?.set(true)
        parentViewModel?.beneficiary?.value?.let { beneficiary ->
            if (beneficiary.beneficiaryType == SendMoneyBeneficiaryType.CASHPAYOUT.type) {
                parentViewModel?.state?.toolBarTitle =
                    getString(Strings.screen_cash_pickup_funds_display_text_header)
            } else {
                parentViewModel?.state?.toolBarTitle =
                    getString(Strings.screen_funds_local_toolbar_header)
            }
        }
    }

    fun updateFees() {
        val result = when (feeType) {
            FeeType.FLAT.name -> getFlatFee().toString()
            FeeType.TIER.name -> getFeeFromTier().toString()
            else -> {
                "0.0"
            }
        }
        updatedFee.value = result
    }

    override fun handlePressOnView(id: Int) {
        if (R.id.btnConfirm == id) {
                if (!isUaeftsBeneficiary()) {
                    when {
                        isDailyLimitReached() -> errorEvent.call()
                        isOtpRequired() -> createOtp(id = id)
                        else -> proceedToTransferAmount()
                    }
                } else {
                    if (isDailyLimitReached())
                        errorEvent.call()
                    else
                        clickEvent.setValue(id)
                }

        } else {
            clickEvent.setValue(id)
        }
    }

    private fun isDailyLimitReached(): Boolean {
        parentViewModel?.transactionThreshold?.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    state.amount.toDoubleOrNull()?.let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        state.errorDescription =
                            if (enteredAmount > dailyLimit) getString(Strings.common_display_text_daily_limit_error_single_transaction) else getString(
                                Strings.common_display_text_daily_limit_error_single_transaction
                            )
                        return enteredAmount >= remainingDailyLimit
                    } ?: return false
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun isOtpRequired(): Boolean {
        parentViewModel?.transactionThreshold?.value?.let {
            it.totalDebitAmountRemittance?.let { totalSMConsumedAmount ->
                state.amount.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimit?.minus(totalSMConsumedAmount)
                    return enteredAmount >= (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

    override fun proceedToTransferAmount() {
        parentViewModel?.beneficiary?.value?.let { beneficiary ->
            beneficiary.beneficiaryType?.let {
                if (beneficiary.beneficiaryType?.isNotEmpty() == true)
                    when (SendMoneyBeneficiaryType.valueOf(beneficiary.beneficiaryType ?: "")) {
                        SendMoneyBeneficiaryType.CASHPAYOUT -> {
                            beneficiary.id?.let { beneficiaryId ->
                                cashPayoutTransferRequest(beneficiaryId)
                            }
                        }
                        else -> {
                        }
                    }
            }

        }
    }

    private fun createOtp(id: Int = 0) {
        clickEvent.postValue(id) // TODO:update this clickEvent with live data it creates debounce
    }

    override fun getPurposeOfPayment(productCode:String) {
        launch {
            when (val response =
                transactionRepository.getTransactionInternationalReasonList(productCode)) {
                is RetroApiResponse.Success -> {
                    if (!response.data.data.isNullOrEmpty()) {
//                        purposeOfPaymentList.value =
                        // response.data.data as? ArrayList<PurposeOfPayment>?
                        transactionData.value = response.data.data
                    } else {
                        state.toast = "Reasons list not found"
                        isAPIFailed.value = true
                    }
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                    isAPIFailed.value = true
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
                        "AED",
                        "8",
                        beneficiaryId,
                        state.noteValue
                    )
                )
                ) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.transferData?.value?.referenceNumber = response.data.data
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

    override fun getTransferFees(productCode: String?) {
        launch {
            state.loading = true
            when (val response =
                transactionRepository.getTransactionFeeWithProductCode(
                    productCode,
                    RemittanceFeeRequest(parentViewModel?.beneficiary?.value?.country, "")
                )) {
                is RetroApiResponse.Success -> {
                    feeType = response.data.data?.feeType ?: ""
                    response.data.data?.tierRateDTOList?.let {
                        feeTiers =
                            response.data.data?.tierRateDTOList as ArrayList<RemittanceFeeResponse.RemittanceFee.TierRateDTO>
                        updateFees()
                    }

                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                    isAPIFailed.value = true
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
                    isAPIFailed.value = true
                }
            }
        }

    }

    override fun getCountryLimit() {
        launch {
            when (val response = customersRepository.getCountryTransactionLimits(
                parentViewModel?.beneficiary?.value?.country ?: "",
                parentViewModel?.beneficiary?.value?.currency ?: ""
            )) {
                is RetroApiResponse.Success -> {
                    setMaxMinLimits(response.data.data?.toDoubleOrNull())
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    isAPIFailed.value = true
                }
            }
        }
    }

    override fun getTransactionThresholds() {
        launch {
            when (val response = transactionRepository.getTransactionThresholds()) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.transactionThreshold?.value = response.data.data
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


    private fun getFeeFromTier(): String? {
//        if (shouldFeeApply()) {

//    }else null
            return if (!state.amount.isBlank()) {
                val fee = feeTiers.filter { item ->
                    item.amountFrom ?: 0.0 <= state.amount.parseToDouble() && item.amountTo ?: 0.0 >= state.amount.parseToDouble()
                }
                if (fee[0].feeAmount != null && fee[0].vatAmount != null) {
                    fee[0].feeAmount?.plus(fee[0].vatAmount ?: 0.0).toString()
                } else {
                    val calculatedFee =
                        (state.amount.parseToDouble() * (fee[0].percentageFee?.parseToDouble()
                            ?: 0.0)).div(100).also {
                            it.plus((it * (fee[0].percentageFee?.parseToDouble() ?: 0.0)).div(100))
                        }
                    calculatedFee.toString()
                }
            } else {
                null
            }
    }

    fun getTotalAmountWithFee(): Double {
        return if (shouldFeeApply()) {
            return (when (feeType) {
                FeeType.TIER.name -> {
                    val transferFee = getFeeFromTier()
                    state.amount.toDoubleOrNull() ?: 0.0.plus(
                        transferFee?.toDoubleOrNull() ?: 0.0
                    )
                }
                FeeType.FLAT.name -> {
                    state.amount.parseToDouble().plus(getFlatFee())
                }
                else -> {
                    state.amount.parseToDouble()
                }
            })
        } else {
            state.amount.parseToDouble()
        }
    }


    private fun getFlatFee(): Double {
//        if (shouldFeeApply())
        //else 0.0

        return if (feeTiers[0].feeAmount != null && feeTiers[0].vatAmount != null) {
                feeTiers[0].feeAmount?.plus(feeTiers[0].vatAmount ?: 0.0) ?: 0.0
            } else {
                return (state.amount.parseToDouble() * (feeTiers[0].percentageFee?.parseToDouble()
                    ?: 0.0)).div(100).also {
                    it.plus((it * (feeTiers[0].percentageFee?.parseToDouble() ?: 0.0)).div(100))
                }
            }

    }

    override fun processPurposeList(list: ArrayList<PurposeOfPayment>) {
        purposeCategories = list.groupBy { item ->
            item.purposeCategory
        }
    }

    fun isUaeftsBeneficiary(): Boolean {
        parentViewModel?.beneficiary?.value?.beneficiaryType?.let {
            return (it == SendMoneyBeneficiaryType.UAEFTS.type || it == SendMoneyBeneficiaryType.DOMESTIC.type)
        } ?: return false
    }

    fun shouldFeeApply(): Boolean {
        return if (!isUaeftsBeneficiary()) return true else
            parentViewModel?.selectedPop?.let { pop ->
                return@let if (pop.nonChargeable == false) {
                    if (pop.cbwsi == true && pop.cbwsiFee == true) {
                        state.amount.toDoubleOrNull() ?: 0.0 > 10000
                    } else
                        false
                } else
                    false
            } ?: false
    }
}