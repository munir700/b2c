package co.yap.modules.dashboard.yapit.y2y.transfer.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentY2yFundsTransferBinding
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.modules.dashboard.yapit.y2y.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2y.transfer.viewmodels.Y2YFundsTransferViewModel
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.LogoData
import co.yap.modules.otp.OtpDataModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.*


class Y2YTransferFragment : Y2YBaseFragment<IY2YFundsTransfer.ViewModel>(), IY2YFundsTransfer.View {
    val args: Y2YTransferFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_y2y_funds_transfer

    override val viewModel: Y2YFundsTransferViewModel
        get() = ViewModelProviders.of(this).get(Y2YFundsTransferViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.availableBalance = MyUserManager.cardBalance.value?.availableBalance
        viewModel.getTransferFees(TransactionProductCode.Y2Y_TRANSFER.pCode)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
        setEditTextWatcher()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
//        viewModel.transferFundSuccess.observe(this, transferFundSuccessObserver)
        viewModel.isFeeReceived.observe(this, Observer {
            if (it) viewModel.updateFees(viewModel.state.amount ?: "")
        })
        viewModel.updatedFee.observe(this, Observer {
            if (it.isNotBlank()) setSpannableFee(it)
        })

    }

    private fun setSpannableFee(feeAmount: String?) {
        viewModel.state.transferFee =
            resources.getText(
                getString(Strings.common_text_fee), requireContext().color(
                    R.color.colorPrimaryDark,
                    "${viewModel.state.currencyType} ${feeAmount?.toFormattedCurrency()}"
                )
            )
    }

    /* private val transferFundSuccessObserver = Observer<Boolean> {
         if (it) {
             moveToFundTransferSuccess()
         }
     }*/

    private fun setEditTextWatcher() {
        etAmount.applyAmountFilters()
        etAmount.afterTextChanged {
            if (viewModel.state.amount.isNotEmpty() && viewModel.state.amount.parseToDouble() > 0.0) {
                checkOnTextChangeValidation()
            } else {
                viewModel.state.valid = false
                cancelAllSnackBar()
            }
            viewModel.updateFees(it)
        }
    }

    private fun checkOnTextChangeValidation() {
        when {
            !isBalanceAvailable() -> {
                viewModel.state.valid = false
                showBalanceNotAvailableError()
            }
            isDailyLimitReached() -> {
                viewModel.parentViewModel?.errorEvent?.value = viewModel.state.errorDescription
                viewModel.state.valid = false
            }
            viewModel.state.amount.parseToDouble() < viewModel.state.minLimit -> {
                viewModel.state.valid = true
            }
            viewModel.state.amount.parseToDouble() > viewModel.state.maxLimit -> {
                showUpperLowerLimitError()
                viewModel.state.valid = false
            }
            else -> {
                cancelAllSnackBar()
                viewModel.state.valid = true
            }
        }
    }

    private fun showUpperLowerLimitError() {
        viewModel.state.errorDescription = Translator.getString(
            requireContext(),
            Strings.common_display_text_min_max_limit_error_transaction,
            viewModel.state.minLimit.toString().toFormattedAmountWithCurrency(),
            viewModel.state.maxLimit.toString().toFormattedAmountWithCurrency()
        )
        viewModel.parentViewModel?.errorEvent?.value = viewModel.state.errorDescription
    }

    private fun showBalanceNotAvailableError() {
        val des = Translator.getString(
            requireContext(),
            Strings.common_display_text_available_balance_error
        ).format(viewModel.state.amount.toFormattedAmountWithCurrency())
        viewModel.parentViewModel?.errorEvent?.value = des
    }

    private fun isBalanceAvailable(): Boolean {
        val availableBalance =
            MyUserManager.cardBalance.value?.availableBalance?.toDoubleOrNull()
        return if (availableBalance != null) {
            (availableBalance >= viewModel.getTotalAmountWithFee())
        } else
            false
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                if (MyUserManager.user?.otpBlocked == true) {
                    showToast(Utils.getOtpBlockedMessage(requireContext()))
                } else {
                    when {
                        viewModel.state.amount.parseToDouble() < viewModel.state.minLimit -> {
                            showUpperLowerLimitError()
                        }
                        isOtpRequired() -> {
                            startOtpFragment()
                        }
                        else -> {
                            viewModel.proceedToTransferAmount {
                                moveToFundTransferSuccess()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startOtpFragment() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    OTPActions.Y2Y.name,
                    MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext())
                        ?: "",
                    username = viewModel.state.fullName,
                    amount = viewModel.state.amount,
                    logoData = LogoData(
                        imageUrl = viewModel.state.imageUrl,
                        position = args.position
                    )
                )
            )
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {

                viewModel.proceedToTransferAmount {
                    moveToFundTransferSuccess()
                }
            }
        }
    }

    private fun setUpData() {
        viewModel.state.fullName = args.beneficiaryName
        viewModel.receiverUUID = args.receiverUUID
        viewModel.state.imageUrl = args.imagePath
        getBinding().lyUserImage.tvNameInitials.background = Utils.getContactBackground(
            getBinding().lyUserImage.tvNameInitials.context,
            args.position
        )

        getBinding().lyUserImage.tvNameInitials.setTextColor(
            Utils.getContactColors(
                getBinding().lyUserImage.tvNameInitials.context, args.position
            )
        )

        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " +
                    viewModel.state.availableBalance?.toFormattedCurrency()
        etAmount.applyAmountFilters()
    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.state.amount.parseToDouble().let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        viewModel.state.errorDescription =
                            when {
                                dailyLimit == totalConsumedAmount -> getString(Strings.common_display_text_daily_limit_error)
                                enteredAmount > dailyLimit && totalConsumedAmount == 0.0 -> {
                                    getString(Strings.common_display_text_daily_limit_error_single_transaction)
                                }
                                else -> getString(Strings.common_display_text_daily_limit_error_multiple_transactions)
                            }
                        return enteredAmount > remainingDailyLimit.roundVal()
                    }
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun isOtpRequired(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.totalDebitAmountY2Y?.let { totalY2YConsumedAmount ->
                viewModel.state.amount?.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimitY2Y?.minus(totalY2YConsumedAmount)
                    return enteredAmount > (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun moveToFundTransferSuccess() {
        // Send Broadcast for updating transactions list in `Home Fragment`
        val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        val action =
            Y2YTransferFragmentDirections.actionY2YTransferFragmentToY2YFundsTransferSuccessFragment(
                viewModel.state.fullName, viewModel.state.imageUrl,
                "AED",
                viewModel.state.amount ?: "", args.position
            )
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        cancelAllSnackBar()
        super.onDestroy()
    }

    override fun getBinding(): FragmentY2yFundsTransferBinding {
        return viewDataBinding as FragmentY2yFundsTransferBinding
    }

    override fun onBackPressed(): Boolean {
        viewModel.parentViewModel?.state?.rightButtonVisibility = true
        return super.onBackPressed()
    }
}
