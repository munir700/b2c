package co.yap.sendMoney.addbeneficiary.fragments

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
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentInternationalFundsTransferBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.InternationalFundsTransferViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.spinneradapter.ViewHolderArrayAdapter
import co.yap.yapcore.enums.SendMoneyBeneficiaryProductCode
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_international_funds_transfer.*


class InternationalFundsTransferFragment :
    SendMoneyBaseFragment<IInternationalFundsTransfer.ViewModel>(),
    IInternationalFundsTransfer.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_international_funds_transfer
    override val viewModel: IInternationalFundsTransfer.ViewModel
        get() = ViewModelProviders.of(this).get(InternationalFundsTransferViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBeneficiaryId()
        val productCode = getProductCode()
        viewModel.getMoneyTransferLimits(productCode)
        viewModel.getTransactionFeeInternational(productCode)
        viewModel.getReasonList(productCode)
        viewModel.getTransactionInternationalfxList(productCode)
        viewModel.getTransactionThresholds1()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //successOtpFlow()
        viewModel.state.availableBalanceString =
            resources.getText(
                getString(Strings.screen_cash_transfer_display_text_available_balance),
                requireContext().color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${MyUserManager.cardBalance.value?.availableBalance?.toFormattedCurrency()}"
                )
            )
        getBindings().etSenderAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), co.yap.widgets.DecimalDigitsInputFilter(2))
        getBindings().etSenderAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.state.clearError()
                viewModel.state.checkValidation()
                if (!viewModel.state.fxRateAmount.isNullOrEmpty()) {
                    val totalAmount = viewModel.state.fxRateAmount?.toDoubleOrNull() ?: 0.0.plus(
                        viewModel.state.transferFeeAmount
                    )
                    viewModel.state.totalTransferAmount.set(totalAmount)
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
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (p0?.length!! > 0) {
//                    getBindings().etSenderAmount.gravity = Gravity.CENTER
//                } else {
//                    getBindings().etSenderAmount.gravity = Gravity.START or Gravity.CENTER_VERTICAL
//                }
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

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.populateSpinnerData.observe(this, Observer {
            if (it == null) return@Observer
            setSpinnerAdapter(it)
        })
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
                viewModel.state.reasonTransferValue = data[position].reason
                viewModel.state.reasonTransferCode = data[position].code
            }
        }
        reasonsSpinner.setSelection(viewModel.reasonPosition)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                if (viewModel.state.reasonTransferValue.equals("Select a Reason")) {
                    toast("Select a Reason")
                } else {
                    /*val totalAmount = viewModel.state.fxRateAmount?.toDoubleOrNull() ?: 0.0.plus(
                        viewModel.state.transferFeeAmount
                    )
                    viewModel.state.totalTransferAmount.set(totalAmount)*/
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
        if (availableBalance != null) {
            val totalTransferAmount = viewModel.state.fxRateAmount?.toDoubleOrNull() ?: 0.0
            +viewModel.state.transferFeeAmount

            return (availableBalance > totalTransferAmount)
        } else
            return false
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
        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).viewModel.errorEvent.value =
                des
        }
    }

    private fun showLimitError() {
        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).viewModel.errorEvent.value =
                viewModel.state.errorDescription
        }
    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.state.totalTransferAmount.get()?.let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)

                        viewModel.state.errorDescription =
                            if (enteredAmount > dailyLimit) getString(Strings.common_display_text_daily_limit_error_single_transaction) else getString(
                                Strings.common_display_text_daily_limit_error_single_transaction
                            )

                        return (enteredAmount >= remainingDailyLimit)

                    } ?: return false
                } ?: return false
            } ?: return false
        } ?: return false

    }

    private fun moveToConfirmTransferScreen() {
        viewModel.state.position?.let { position ->
            viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                val action =
                    InternationalFundsTransferFragmentDirections.actionInternationalFundsTransferFragmentToInternationalTransactionConfirmationFragment(
                        viewModel.state.beneficiaryName,
                        viewModel.state.senderCurrency.toString(),
                        viewModel.state.fxRateAmount.toString(),
                        viewModel.state.receiverCurrencyAmount.toString(),
                        viewModel.state.toFxRateCurrency ?: "",
                        viewModel.state.totalAmount.toString(),
                        viewModel.state.fromFxRate.toString(),
                        viewModel.state.toFxRate.toString(),
                        viewModel.state.referenceNumber.toString(),
                        position,
                        beneficiaryCountry,
                        viewModel.state.firstName
                            ?: viewModel.state.beneficiaryName,
                        viewModel.state.fromFxRateCurrency ?: "",
                        viewModel.state.reasonTransferCode ?: "",
                        viewModel.state.transactionNote ?: "",
                        viewModel.state.reasonTransferValue ?: "",
                        viewModel.state.rate ?: "",
                        viewModel.otpAction ?: "",
                        viewModel.state.srRate
                    )
                findNavController().navigate(action)
            }

        }

    }

    private fun editBeneficiaryScreen() {
//    etnickName.isEnabled = true
//    etFirstName.isEnabled = true
//    etLastName.isEnabled = true
//    etAccountIbanNumber.isEnabled = true
//    etnickName.isEnabled = true
//    etSwiftCode.isEnabled = true
//    etBankREquiredFieldCode.isEnabled = true
    }


    private fun getProductCode(): String {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).let { beneficiaryCashTransaferActivity ->
                beneficiaryCashTransaferActivity.viewModel.state.toolBarTitle = getString(
                    Strings.screen_funds_toolbar_header
                )
                viewModel.state.position = beneficiaryCashTransaferActivity.viewModel.state.position
            }
            (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
                viewModel.state.beneficiaryCountry = beneficiary.country
                viewModel.state.beneficiaryName = beneficiary.fullName()
                viewModel.state.firstName = beneficiary.firstName
                beneficiary.beneficiaryType?.let { beneficiaryType ->
                    if (beneficiaryType.isNotEmpty())
                        return when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                            SendMoneyBeneficiaryType.RMT -> {
                                viewModel.otpAction = SendMoneyBeneficiaryType.RMT.name
                                SendMoneyBeneficiaryProductCode.P012.name
                            }
                            SendMoneyBeneficiaryType.SWIFT -> {
                                viewModel.otpAction = SendMoneyBeneficiaryType.SWIFT.name
                                SendMoneyBeneficiaryProductCode.P011.name
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

    private fun getBeneficiaryId() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
                viewModel.state.beneficiary = beneficiary
                beneficiary.id?.let { beneficiaryId ->
                    viewModel.state.beneficiaryId = beneficiaryId.toString()
                }
            }
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
    }

    fun getBindings(): FragmentInternationalFundsTransferBinding {
        return viewDataBinding as FragmentInternationalFundsTransferBinding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelAllSnackBar()
    }
}