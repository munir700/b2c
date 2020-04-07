package co.yap.sendMoney.fundtransfer.fragments

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import co.yap.sendMoney.PopListBottomSheet
import co.yap.sendMoney.fundtransfer.activities.BeneficiaryFundTransferActivity
import co.yap.sendMoney.fundtransfer.interfaces.IInternationalFundsTransfer
import co.yap.sendMoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentInternationalFundsTransferBinding
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_international_funds_transfer.*


class InternationalFundsTransferFragment :
    BeneficiaryFundTransferBaseFragment<IInternationalFundsTransfer.ViewModel>(),
    IInternationalFundsTransfer.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_international_funds_transfer
    override val viewModel: InternationalFundsTransferViewModel
        get() = ViewModelProviders.of(this).get(InternationalFundsTransferViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productCode = getProductCode()
        viewModel.getMoneyTransferLimits(productCode)
        viewModel.getTransferFees(
            productCode,
            RemittanceFeeRequest(viewModel.parentViewModel?.beneficiary?.value?.country, "")
        )
        viewModel.getReasonList(productCode)
        viewModel.getTransactionInternationalfxList(productCode)
        viewModel.getTransactionThresholds()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.parentViewModel?.selectedPop != null) {
            getBindings().tvSelectReason.text =
                viewModel.parentViewModel?.selectedPop?.purposeDescription
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setEditTextWatcher()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.purposeOfPaymentList.observe(this, Observer {
            it?.let {
                viewModel.processPurposeList(it)
            }
        })
        viewModel.updatedFee.observe(this, Observer {
            if (!it.isNullOrBlank())
                setSpannableFee(it)
        })
        viewModel.isFeeReceived.observe(this, Observer {
            if (it) viewModel.updateFees()
        })
        viewModel.fxRateResponse.observe(this, Observer {
            handleFxRateResponse(it)
        })
        viewModel.isAPIFailed.observe(this, Observer {
            if (it) {
                requireActivity().finish()
            }
        })
    }

    private fun handleFxRateResponse(it: FxRateResponse.Data?) {
        it?.let { fxRate ->
            viewModel.state.fromFxRate =
                "${fxRate.fromCurrencyCode} ${fxRate.fxRates?.get(0)?.rate}"
            viewModel.state.toFxRate =
                "${fxRate.toCurrencyCode} ${fxRate.value?.amount?.toFormattedCurrency()}"
            viewModel.state.sourceCurrency.set(fxRate.fromCurrencyCode)
            viewModel.state.destinationCurrency.set(fxRate.toCurrencyCode)
            viewModel.parentViewModel?.transferData?.value?.rate = fxRate.fxRates?.get(0)?.rate
        }
    }

    private fun setSpannableFee(feeAmount: String?) {
        viewModel.parentViewModel?.transferData?.value?.transferFee = feeAmount
        viewModel.state.transferFeeSpannable = resources.getText(
            getString(Strings.screen_international_funds_transfer_display_text_fee),
            requireContext().color(R.color.colorPrimaryDark, "AED"),
            requireContext().color(
                R.color.colorPrimaryDark,
                if (feeAmount.isNullOrBlank()) "0.00" else feeAmount.toFormattedCurrency() ?: "0.00"
            )
        )
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                if (viewModel.parentViewModel?.selectedPop != null) startFlow() else showToast(
                    "Select a reason"
                )
            }

            R.id.tvSelectReason, R.id.ivSelector -> setupPOP(viewModel.purposeCategories)
        }
    }

    private fun setupPOP(purposeCategories: Map<String?, List<PurposeOfPayment>>?) {
        var inviteFriendBottomSheet: BottomSheetDialogFragment? = null
        this.fragmentManager?.let {
            inviteFriendBottomSheet = PopListBottomSheet(object :
                OnItemClickListener {
                override fun onItemClick(view: View, data: Any, pos: Int) {
                    inviteFriendBottomSheet?.dismiss()
                    viewModel.parentViewModel?.selectedPop = data as PurposeOfPayment
                    viewModel.updateFees()
                    getBindings().tvSelectReason.text =
                        viewModel.parentViewModel?.selectedPop?.purposeDescription
                }

            }, purposeCategories)
            inviteFriendBottomSheet?.show(it, "")
        }
    }

    private fun isBalanceAvailable(): Boolean {
        val availableBalance =
            MyUserManager.cardBalance.value?.availableBalance?.toDoubleOrNull()
        return if (availableBalance != null) {
            (availableBalance > viewModel.getTotalAmountWithFee())
        } else
            false
    }

    private fun startFlow() {
        if (isBalanceAvailable()) {
            if (viewModel.state.etInputAmount.parseToDouble() < viewModel.state.minLimit ?: 0.0 || viewModel.state.etInputAmount.parseToDouble() > viewModel.state.maxLimit ?: 0.0) {
                setLowerAndUpperLimitError()
            } else {
                if (isDailyLimitReached())
                    showLimitError()
                else
                    moveToConfirmTransferScreen()
            }
        } else {
            showBalanceNotAvailableError()
        }
    }

    private fun setLowerAndUpperLimitError() {
        viewModel.state.errorDescription = getString(
            Strings.common_display_text_min_max_limit_error_transaction
        ).format(
            viewModel.state.minLimit.toString().toFormattedCurrency(),
            viewModel.state.maxLimit.toString().toFormattedCurrency()
        )
        showLimitError()
    }

    private fun showBalanceNotAvailableError() {
        val des = Translator.getString(
            requireContext(),
            Strings.common_display_text_available_balance_error
        ).format(MyUserManager.cardBalance.value?.availableBalance?.toFormattedCurrency())
        if (activity is BeneficiaryFundTransferActivity) {
            (activity as BeneficiaryFundTransferActivity).viewModel.errorEvent.value =
                des
        }
    }

    private fun showLimitError() {
        if (activity is BeneficiaryFundTransferActivity) {
            (activity as BeneficiaryFundTransferActivity).viewModel.errorEvent.value =
                viewModel.state.errorDescription
        }
    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.parentViewModel?.transactionThreshold?.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.state.etInputAmount.parseToDouble().let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        viewModel.state.errorDescription =
                            if (enteredAmount > dailyLimit) getString(Strings.common_display_text_daily_limit_error_single_transaction) else getString(
                                Strings.common_display_text_daily_limit_error_single_transaction
                            )
                        return (enteredAmount > remainingDailyLimit)
                    }
                } ?: return false
            } ?: return false
        } ?: return false

    }

    private fun moveToConfirmTransferScreen() {
        viewModel.parentViewModel?.transferData?.value?.feeAmount = viewModel.feeAmount
        viewModel.parentViewModel?.transferData?.value?.vat = viewModel.vat
        viewModel.parentViewModel?.transferData?.value?.sourceCurrency =
            viewModel.state.sourceCurrency.get().toString()
        viewModel.parentViewModel?.transferData?.value?.sourceAmount =
            viewModel.state.etInputAmount.toString()
        viewModel.parentViewModel?.transferData?.value?.destinationCurrency =
            viewModel.state.destinationCurrency.get().toString()
        viewModel.parentViewModel?.transferData?.value?.destinationAmount =
            viewModel.state.etOutputAmount.toString()
        viewModel.parentViewModel?.transferData?.value?.toFxRate = viewModel.state.toFxRate
        viewModel.parentViewModel?.transferData?.value?.fromFxRate = viewModel.state.fromFxRate
        viewModel.parentViewModel?.transferData?.value?.noteValue =
            viewModel.state.transactionNote.get()
        viewModel.parentViewModel?.transferData?.value?.transferAmount =
            viewModel.state.etInputAmount
        val action =
            InternationalFundsTransferFragmentDirections.actionInternationalFundsTransferFragmentToInternationalTransactionConfirmationFragment()
        findNavController().navigate(action)
    }

    private fun getProductCode(): String {
        viewModel.parentViewModel?.beneficiary?.value?.let { beneficiary ->
            when (beneficiary.beneficiaryType) {
                SendMoneyBeneficiaryType.RMT.type -> {
                    viewModel.parentViewModel?.transferData?.value?.otpAction =
                        SendMoneyBeneficiaryType.RMT.name
                    return TransactionProductCode.RMT.pCode
                }
                SendMoneyBeneficiaryType.SWIFT.type -> {
                    viewModel.parentViewModel?.transferData?.value?.otpAction =
                        SendMoneyBeneficiaryType.SWIFT.name
                    return TransactionProductCode.SWIFT.pCode
                }
                else -> return ""
            }
        } ?: return ""
    }

    private fun setEditTextWatcher() {
        etSenderAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

        etSenderAmount.afterTextChanged {
            viewModel.state.clearError()
            if (!viewModel.state.etInputAmount.isNullOrBlank()) {
                checkOnTextChangeValidation()
            }
            viewModel.updateFees()
            viewModel.setDestinationAmount()
        }
    }

    private fun checkOnTextChangeValidation() {
        if (isBalanceAvailable()) {
            if (isDailyLimitReached()) {
                showLimitError()
                viewModel.state.valid = false
            } else {
                cancelAllSnackBar()
                viewModel.state.valid = true
            }
        } else {
            viewModel.state.valid = false
            showBalanceNotAvailableError()
        }
    }

    override fun onResume() {
        super.onResume()
        setObservers()
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
        viewModel.isFeeReceived.removeObservers(this)
        viewModel.updatedFee.removeObservers(this)
        viewModel.fxRateResponse.removeObservers(this)
        viewModel.isAPIFailed.removeObservers(this)
        viewModel.purposeOfPaymentList.removeObservers(this)

    }

    fun getBindings(): FragmentInternationalFundsTransferBinding {
        return viewDataBinding as FragmentInternationalFundsTransferBinding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelAllSnackBar()
    }
}