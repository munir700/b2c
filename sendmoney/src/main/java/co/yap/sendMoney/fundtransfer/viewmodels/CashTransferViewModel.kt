package co.yap.sendMoney.fundtransfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.SendMoneyTransferRequest
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
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
        //if (shouldFeeApply())
        updateFees(state.amount)
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

    override fun getPurposeOfPayment(productCode: String) {
        launch {
            state.loading = true
            when (val response =
                transactionRepository.getPurposeOfPayment(productCode)) {
                is RetroApiResponse.Success -> {
                    if (!response.data.data.isNullOrEmpty()) {
                        purposeOfPaymentList.value =
                            response.data.data as? ArrayList<PurposeOfPayment>?
                    }
                    state.loading = false
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
                    SendMoneyTransferRequest(
                        beneficiaryId = beneficiaryId,
                        amount = state.amount.toDouble(),
                        currency = "AED",
                        purposeCode = "8",
                        remarks = state.noteValue
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

    fun getTotalAmountWithFee(): Double {
        return if (shouldFeeApply()) {
            return (when (feeType) {
                FeeType.TIER.name -> {
                    val transferFee = getFeeFromTier(state.amount)
                    state.amount.toDoubleOrNull() ?: 0.0.plus(
                        transferFee?.toDoubleOrNull() ?: 0.0
                    )
                }
                FeeType.FLAT.name -> {
                    state.amount.parseToDouble().plus(getFlatFee(state.amount).parseToDouble())
                }
                else -> {
                    state.amount.parseToDouble()
                }
            })
        } else {
            state.amount.parseToDouble()
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

    private fun isOnlyUAEFTS(): Boolean {
        parentViewModel?.beneficiary?.value?.beneficiaryType?.let {
            return (it == SendMoneyBeneficiaryType.UAEFTS.type)
        } ?: return false
    }

    fun shouldFeeApply(): Boolean {
        return if (!isOnlyUAEFTS()) return true else
            parentViewModel?.selectedPop?.let { pop ->
                return@let if (pop.nonChargeable == false) {
                    return (when {
                        pop.cbwsi == true && pop.cbwsiFee == false -> state.amount.parseToDouble() > parentViewModel?.transactionThreshold?.value?.cbwsiPaymentLimit ?: 0.0
                        else -> true
                    })
                } else
                    false
            } ?: true
    }
}