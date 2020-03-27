package co.yap.sendMoney.fundtransfer.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.LogoData
import co.yap.modules.otp.OtpDataModel
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.sendMoney.fundtransfer.activities.BeneficiaryFundTransferActivity
import co.yap.sendMoney.fundtransfer.interfaces.ICashTransfer
import co.yap.sendMoney.fundtransfer.viewmodels.CashTransferViewModel
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentCashTransferBinding
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.spinneradapter.ViewHolderArrayAdapter
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_cash_transfer.*

class CashTransferFragment : BeneficiaryFundTransferBaseFragment<ICashTransfer.ViewModel>(),
    ICashTransfer.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_cash_transfer

    override val viewModel: CashTransferViewModel
        get() = ViewModelProviders.of(this).get(CashTransferViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startFlows(getProductCode())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updatedFee.value = "0.0"
        setEditTextWatcher()
        if (viewModel.transactionData.size > 0)
            setSpinnerAdapter(viewModel.transactionData)

    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, Observer {
            viewModel.parentViewModel?.errorEvent?.value = viewModel.state.errorDescription
        })

        viewModel.isAPIFailed.observe(this, Observer {
            if (it) requireActivity().finish()
        })

        viewModel.updatedFee.observe(this, Observer {
            if (!it.isNullOrBlank())
                setSpannableFee(it)
        })

        viewModel.populateSpinnerData.observe(this, Observer {
            if (it == null) return@Observer
            setSpinnerAdapter(it)
        })
        viewModel.purposeOfPaymentList.observe(this, Observer {
            it?.let {
                viewModel.processPurposeList(it)
            }
        })
    }

    private fun setSpannableFee(totalFeeAmount: String?) {
        viewModel.parentViewModel?.transferData?.value?.transferFee = totalFeeAmount
        viewModel.state.feeAmountSpannableString = resources.getText(
            getString(Strings.screen_cash_pickup_funds_display_text_fee),
            requireContext().color(R.color.colorPrimaryDark, "AED"),
            requireContext().color(
                R.color.colorPrimaryDark,
                if (totalFeeAmount.isNullOrBlank()) "0.00" else totalFeeAmount.toFormattedCurrency()
                    ?: "0.00"
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
        reasonsSpinnerCashTransfer.adapter =
            ViewHolderArrayAdapter(requireContext(), data, { parent ->
                ReasonDropDownViewHolder.inflateSelectedView(
                    parent
                )
            }, { parent ->
                ReasonDropDownViewHolder.inflate(
                    parent
                )
            }, { viewHolder, position, item ->
                viewHolder.bind(item)
            }, { viewHolder, position, item ->
                viewHolder.bind(item)
            })
        reasonsSpinnerCashTransfer.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        viewModel.parentViewModel?.selectedPop =
                            viewModel.purposeOfPaymentList.value?.get(position - 1)

                        viewModel.parentViewModel?.selectedPop?.nonChargeable = false
                        viewModel.parentViewModel?.selectedPop?.cbwsi = true
                        viewModel.parentViewModel?.selectedPop?.cbwsiFee = true

                        if (viewModel.shouldFeeApply())
                            viewModel.updateFees()
                    }

                    viewModel.reasonPosition = position
                    viewModel.parentViewModel?.transferData?.value?.purposeCode =
                        data[position].code
                    viewModel.parentViewModel?.transferData?.value?.transferReason =
                        data[position].reason
                }
            }
        reasonsSpinnerCashTransfer.setSelection(viewModel.reasonPosition)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> if (viewModel.isUaeftsBeneficiary()) moveToConfirmationScreen() else startOtpFragment()
            R.id.viewTriggerSpinnerClickReasonCash -> reasonsSpinnerCashTransfer.performClick()
            Constants.ADD_CASH_PICK_UP_SUCCESS -> {
                // Send Broadcast for updating transactions list in `Home Fragment`
                val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                viewModel.parentViewModel?.transferData?.value?.sourceCurrency = "AED";
                viewModel.parentViewModel?.transferData?.value?.transferAmount =
                    viewModel.state.amount
                val action =
                    CashTransferFragmentDirections.actionCashTransferFragmentToTransferSuccessFragment2()
                findNavController().navigate(action)

            }
        }
    }

    private fun startOtpFragment() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    viewModel.parentViewModel?.transferData?.value?.otpAction,//action,
                    MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext())
                        ?: "",
                    username = viewModel.parentViewModel?.beneficiary?.value?.fullName(),
                    amount = viewModel.state.amount,
                    logoData = LogoData(
                        position = viewModel.parentViewModel?.transferData?.value?.position
                    )
                )
            )
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
                viewModel.proceedToTransferAmount()
            }
        }
    }

    private fun moveToConfirmationScreen() {
        viewModel.parentViewModel?.transferData?.value?.transferAmount = viewModel.state.amount
        viewModel.parentViewModel?.transferData?.value?.noteValue = viewModel.state.noteValue
        viewModel.parentViewModel?.transferData?.value?.sourceCurrency = "AED"
        val action =
            CashTransferFragmentDirections.actionCashTransferFragmentToCashTransferConfirmationFragment()
        findNavController().navigate(action)
    }

    private fun showBalanceNotAvailableError() {
        val des = Translator.getString(
            requireContext(),
            Strings.common_display_text_available_balance_error
        ).format(MyUserManager.cardBalance.value?.availableBalance?.toFormattedCurrency())
        viewModel.parentViewModel?.errorEvent?.value = des
    }

    private fun showLimitError() {
        if (activity is BeneficiaryFundTransferActivity) {
            (activity as BeneficiaryFundTransferActivity).viewModel.errorEvent.value =
                viewModel.state.errorDescription
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

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.state.amount.toDoubleOrNull()?.let { enteredAmount ->
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

    private fun startFlows(produceCode: String) {
        viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType?.let { beneficiaryType ->
            when (beneficiaryType) {
                SendMoneyBeneficiaryType.RMT.type, SendMoneyBeneficiaryType.SWIFT.type -> skipCashTransferFragment()
                else -> {
                    viewModel.getMoneyTransferLimits(viewModel.state.produceCode)
                    viewModel.getTransferFees(viewModel.state.produceCode)
                    viewModel.getPurposeOfPayment()
                    viewModel.getCashTransferReasonList()
                    setObservers()
                }
            }
        }
    }

    private fun getProductCode(): String {
        viewModel.parentViewModel?.beneficiary?.value?.let { beneficiary ->
            beneficiary.beneficiaryType?.let { beneficiaryType ->
                when (beneficiaryType) {
                    SendMoneyBeneficiaryType.RMT.type -> {
                        setOtpAction(
                            SendMoneyBeneficiaryType.RMT.type,
                            TransactionProductCode.RMT.pCode
                        )
                        return viewModel.state.produceCode ?: ""
                    }
                    SendMoneyBeneficiaryType.SWIFT.type -> {
                        setOtpAction(
                            SendMoneyBeneficiaryType.SWIFT.type,
                            TransactionProductCode.SWIFT.pCode
                        )
                        return viewModel.state.produceCode ?: ""
                    }
                    SendMoneyBeneficiaryType.CASHPAYOUT.type -> {
                        setOtpAction(
                            SendMoneyBeneficiaryType.CASHPAYOUT.type,
                            TransactionProductCode.CASH_PAYOUT.pCode
                        )
                        return viewModel.state.produceCode ?: ""
                    }
                    SendMoneyBeneficiaryType.DOMESTIC.type -> {
                        setOtpAction(
                            SendMoneyBeneficiaryType.DOMESTIC_TRANSFER.type,
                            TransactionProductCode.DOMESTIC.pCode
                        )
                        return viewModel.state.produceCode ?: ""
                    }
                    SendMoneyBeneficiaryType.UAEFTS.type -> {
                        setOtpAction(
                            SendMoneyBeneficiaryType.UAEFTS.type,
                            TransactionProductCode.UAEFTS.pCode
                        )
                        return viewModel.state.produceCode ?: ""
                    }
                    else -> {
                        return TransactionProductCode.UAEFTS.pCode
                    }
                }
            } ?: return ""
        } ?: return ""
    }

    private fun setOtpAction(action: String, productCode: String) {
        viewModel.parentViewModel?.transferData?.value?.otpAction = action
        viewModel.state.produceCode = productCode
    }

    override fun onPause() {
        super.onPause()
        viewModel.isAPIFailed.removeObservers(this)
        viewModel.purposeOfPaymentList.removeObservers(this)
        viewModel.updatedFee.removeObservers(this)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.isAPIFailed.removeObservers(this)
        viewModel.updatedFee.removeObservers(this)
        viewModel.purposeOfPaymentList.removeObservers(this)
        super.onDestroy()
    }

    private fun skipCashTransferFragment() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.cashTransferFragment, true) // starting destination skiped
            .build()

        findNavController().navigate(
            R.id.action_cashTransferFragment_to_internationalFundsTransferFragment,
            null,
            navOptions
        )

    }

    class ReasonDropDownViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun inflate(parent: ViewGroup): ReasonDropDownViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_spinner, parent, false)
                return ReasonDropDownViewHolder(
                    view
                )
            }

            fun inflateSelectedView(parent: ViewGroup): ReasonDropDownViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_selected_spinner, parent, false)
                return ReasonDropDownViewHolder(
                    view
                )
            }
        }

        var title: TextView = view.findViewById(R.id.textView)


        fun bind(reason: InternationalFundsTransferReasonList.ReasonList) {
            title.text = reason.reason
        }
    }

    private fun setEditTextWatcher() {
        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

        etAmount.afterTextChanged {
            viewModel.state.clearError()
            if (viewModel.state.amount.isNotEmpty()) {
                viewModel.updateFees()
                checkOnTextChangeValidation()
            }
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

    fun getBindings(): FragmentCashTransferBinding {
        return viewDataBinding as FragmentCashTransferBinding
    }
}