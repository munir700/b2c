package co.yap.sendmoney.fundtransfer.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.LogoData
import co.yap.modules.otp.OtpDataModel
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.sendmoney.PopListBottomSheet
import co.yap.sendmoney.fundtransfer.activities.BeneficiaryFundTransferActivity
import co.yap.sendmoney.fundtransfer.interfaces.ICashTransfer
import co.yap.sendmoney.fundtransfer.viewmodels.CashTransferViewModel
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentCashTransferBinding
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.extentions.toFormattedAmountWithCurrency
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
        if (viewModel.parentViewModel?.selectedPop != null) {
            getBindings().tvSelectReason.text =
                viewModel.parentViewModel?.selectedPop?.purposeDescription
        }
        setEditTextWatcher()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, Observer {
            viewModel.parentViewModel?.errorEvent?.value = viewModel.state.errorDescription
        })

        viewModel.isAPIFailed.observe(this, Observer {
            //if (it) requireActivity().finish()
        })

        viewModel.isFeeReceived.observe(this, Observer {
            if (it) viewModel.updateFees()
        })
        viewModel.updatedFee.observe(this, Observer {
            if (!it.isNullOrBlank())
                setSpannableFee(if (viewModel.shouldFeeApply()) it else "0.0")
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
                    isDailyLimitReached()
                }

            }, purposeCategories)
            inviteFriendBottomSheet?.show(it, "")
        }
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                if (viewModel.isUaeftsBeneficiary()) {
                    if (viewModel.parentViewModel?.selectedPop != null) moveToConfirmationScreen() else showToast(
                        "Select a reason"
                    )
                } else
                    startOtpFragment()
            }
            R.id.tvSelectReason, R.id.ivSelector -> setupPOP(viewModel.purposeCategories)
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
        viewModel.parentViewModel?.transferData?.value?.feeAmount =
            if (viewModel.shouldFeeApply()) viewModel.feeAmount else "0.0"
        viewModel.parentViewModel?.transferData?.value?.vat =
            if (viewModel.shouldFeeApply()) viewModel.vat else "0.0"

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
        viewModel.parentViewModel?.transactionThreshold?.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.state.amount.toDoubleOrNull()?.let { enteredAmount ->
                        if (viewModel.trxWillHold() && viewModel.transactionMightGetHeld.value == true) {
                            val totalHoldAmount =
                                (it.holdSwiftAmount ?: 0.0).plus(it.holdUAEFTSAmount ?: 0.0)
                            val remainingDailyLimit =
                                if ((dailyLimit - totalHoldAmount) < 0.0) 0.0 else (dailyLimit - totalHoldAmount)
                            viewModel.state.errorDescription =
                                "You have exceeded your limit for held on transactions, please enter an amount less than %1s".format(
                                    (dailyLimit - totalHoldAmount).toString().toFormattedAmountWithCurrency()
                                )
                            return (enteredAmount > remainingDailyLimit)


                        } else {
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
        } ?: return false

    }

    private fun startFlows(productCode: String) {
        viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType?.let { beneficiaryType ->
            when (beneficiaryType) {
                SendMoneyBeneficiaryType.RMT.type, SendMoneyBeneficiaryType.SWIFT.type -> skipCashTransferFragment()
                else -> {
                    viewModel.getMoneyTransferLimits(productCode)
                    viewModel.getTransferFees(
                        productCode,
                        RemittanceFeeRequest(
                            viewModel.parentViewModel?.beneficiary?.value?.country,
                            ""
                        )
                    )
                    viewModel.getPurposeOfPayment(productCode)
                    setObservers()
                }
            }
        }
    }

    private fun getProductCode(): String {
        viewModel.parentViewModel?.beneficiary?.value?.let { beneficiary ->
            when (beneficiary.beneficiaryType) {
                SendMoneyBeneficiaryType.CASHPAYOUT.type -> {
                    viewModel.parentViewModel?.transferData?.value?.otpAction =
                        SendMoneyBeneficiaryType.CASHPAYOUT.type
                    return TransactionProductCode.CASH_PAYOUT.pCode
                }
                SendMoneyBeneficiaryType.DOMESTIC.type -> {
                    viewModel.parentViewModel?.transferData?.value?.otpAction =
                        SendMoneyBeneficiaryType.DOMESTIC_TRANSFER.type
                    return TransactionProductCode.DOMESTIC.pCode
                }
                SendMoneyBeneficiaryType.UAEFTS.type -> {
                    viewModel.parentViewModel?.transferData?.value?.otpAction =
                        SendMoneyBeneficiaryType.UAEFTS.type
                    return TransactionProductCode.UAEFTS.pCode
                }
                else -> {
                    return ""
                }
            }
        } ?: return ""
    }

    override fun onPause() {
        super.onPause()
        viewModel.isAPIFailed.removeObservers(this)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.isAPIFailed.removeObservers(this)
        viewModel.updatedFee.removeObservers(this)
        viewModel.purposeOfPaymentList.removeObservers(this)
        viewModel.transactionData.removeObservers(this)
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

    private fun setEditTextWatcher() {
        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

        etAmount.afterTextChanged {
            viewModel.state.clearError()
            if (viewModel.state.amount.isNotEmpty()) {
                checkOnTextChangeValidation()
            }
            viewModel.updateFees()
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