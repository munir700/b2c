package co.yap.sendmoney.fundtransfer.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.sendmoney.fundtransfer.activities.BeneficiaryFundTransferActivity
import co.yap.sendmoney.fundtransfer.interfaces.IInternationalFundsTransfer
import co.yap.sendmoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentInternationalFundsTransferBinding
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.spinneradapter.ViewHolderArrayAdapter
import co.yap.yapcore.enums.FeeType
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
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
        setSpannableFee("0.00")
        val productCode = getProductCode()
        viewModel.getMoneyTransferLimits(productCode)
        viewModel.getTransactionFeeInternational(productCode)
        viewModel.getReasonList(productCode)
        viewModel.getTransactionInternationalfxList(productCode)
        viewModel.getTransactionThresholds()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.availableBalanceString =
            resources.getText(
                getString(Strings.screen_cash_transfer_display_text_available_balance),
                requireContext().color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${MyUserManager.cardBalance.value?.availableBalance?.toFormattedCurrency()}"
                )
            )
        setEditTextWatcher()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.populateSpinnerData.observe(this, Observer {
            if (it == null) return@Observer
            setSpinnerAdapter(it)
        })
        viewModel.transactionFeeResponse.observe(this, Observer {
            handleTxnFeeResponse(it)
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

    private fun handleTxnFeeResponse(feeResponse: RemittanceFeeResponse.RemittanceFee?) {
        when (feeResponse?.feeType) {
            FeeType.FLAT.name -> {
                handleFlatFee(feeResponse)
            }

            FeeType.TIER.name -> viewModel.feeTiers =
                feeResponse.tierRateDTOList as ArrayList<RemittanceFeeResponse.RemittanceFee.TierRateDTO>

            FeeType.PERCENTAGE.name -> {

            }
        }
    }

    private fun handleFlatFee(feeResponse: RemittanceFeeResponse.RemittanceFee) {
        val totalFeeAmount = feeResponse.tierRateDTOList?.get(0)
            ?.feeAmount?.plus(feeResponse.tierRateDTOList?.get(0)?.vatAmount ?: 0.0)
        setSpannableFee(totalFeeAmount.toString())
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

    private fun setSpinnerAdapter(list: ArrayList<InternationalFundsTransferReasonList.ReasonList>) {
        val data = ArrayList<InternationalFundsTransferReasonList.ReasonList>()
        data.addAll(list)
        data.add(
            0,
            InternationalFundsTransferReasonList.ReasonList("Select a Reason", "0")
        )
        reasonsSpinner.adapter = ViewHolderArrayAdapter(requireContext(), data, { parent ->
            CashTransferFragment.ReasonDropDownViewHolder.inflateSelectedView(parent)
        }, { parent ->
            CashTransferFragment.ReasonDropDownViewHolder.inflate(parent)
        }, { viewHolder, position, item ->
            viewHolder.bind(item)
        }, { viewHolder, position, item ->
            viewHolder.bind(item)
        })
        reasonsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.reasonPosition = position
                viewModel.parentViewModel?.transferData?.value?.purposeCode = data[position].code
                viewModel.parentViewModel?.transferData?.value?.transferReason =
                    data[position].reason
            }
        }
        reasonsSpinner.setSelection(viewModel.reasonPosition)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                if (viewModel.parentViewModel?.transferData?.value?.transferReason.equals("Select a Reason")) {
                    toast("Select a Reason")
                } else {
                    startFlow()
                }
            }

            R.id.viewSpinnerClickReason -> {
                reasonsSpinner.performClick()
            }
        }
    }

    private fun isBalanceAvailable(): Boolean {
        val availableBalance =
            MyUserManager.cardBalance.value?.availableBalance?.toDoubleOrNull()
        return if (availableBalance != null) {
            (availableBalance > viewModel.state.totalTransferAmount.get() ?: 0.0)
        } else
            false
    }

    private fun startFlow() {
        if (isBalanceAvailable()) {
            if (viewModel.state.totalTransferAmount.get() ?: 0.0 < viewModel.state.minLimit ?: 0.0 || viewModel.state.totalTransferAmount.get() ?: 0.0 > viewModel.state.maxLimit ?: 0.0) {
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
                    viewModel.state.totalTransferAmount.get()?.let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        viewModel.state.errorDescription =
                            if (enteredAmount > dailyLimit) getString(Strings.common_display_text_daily_limit_error_single_transaction) else getString(
                                Strings.common_display_text_daily_limit_error_single_transaction
                            )
                        return (enteredAmount > remainingDailyLimit)
                    } ?: return false
                } ?: return false
            } ?: return false
        } ?: return false

    }

    private fun moveToConfirmTransferScreen() {
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
        if (context is BeneficiaryFundTransferActivity) {
            viewModel.parentViewModel?.beneficiary?.value?.let { beneficiary ->
                beneficiary.beneficiaryType?.let { beneficiaryType ->
                    if (beneficiaryType.isNotEmpty())
                        return when (beneficiaryType) {
                            SendMoneyBeneficiaryType.RMT.type -> {
                                viewModel.parentViewModel?.transferData?.value?.otpAction =
                                    SendMoneyBeneficiaryType.RMT.name
                                TransactionProductCode.RMT.pCode
                            }
                            SendMoneyBeneficiaryType.SWIFT.type -> {
                                viewModel.parentViewModel?.transferData?.value?.otpAction =
                                    SendMoneyBeneficiaryType.SWIFT.name
                                TransactionProductCode.SWIFT.pCode
                            }
                            else -> {
                                ""
                            }
                        }
                }
            }
        }
        return ""
    }

    private fun setEditTextWatcher() {
        getBindings().etSenderAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), co.yap.widgets.DecimalDigitsInputFilter(2))
        getBindings().etSenderAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.state.clearError()
                if (!viewModel.state.etInputAmount.isNullOrEmpty()) {
                    viewModel.state.totalTransferAmount.set(viewModel.getTotalAmountWithFee())
                    if (viewModel.transactionFeeResponse.value?.feeType == FeeType.TIER.name)
                        setSpannableFee(viewModel.getFeeFromTier(viewModel.state.etInputAmount))

                    checkOnTextChangeValidation()
                }
                viewModel.setDestinationAmount()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length ?: 0 > 0) {
                    getBindings().etSenderAmount.gravity =
                        Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                } else {
                    getBindings().etSenderAmount.gravity =
                        Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                }
            }
        })
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
        viewModel.populateSpinnerData.removeObservers(this)
        viewModel.transactionFeeResponse.removeObservers(this)
        viewModel.fxRateResponse.removeObservers(this)
        viewModel.isAPIFailed.removeObservers(this)

    }

    fun getBindings(): FragmentInternationalFundsTransferBinding {
        return viewDataBinding as FragmentInternationalFundsTransferBinding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelAllSnackBar()
    }
}