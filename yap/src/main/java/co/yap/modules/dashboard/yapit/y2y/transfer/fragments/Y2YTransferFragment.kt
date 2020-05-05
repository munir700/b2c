package co.yap.modules.dashboard.yapit.y2y.transfer.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.Gravity
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
import co.yap.yapcore.helpers.*
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.snackbar.Snackbar
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
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.enteredAmount.observe(this, enterAmountObserver)
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_LONG)

        })
        viewModel.transferFundSuccess.observe(this, transferFundSuccessObserver)
        viewModel.isFeeReceived.observe(this, Observer {
            if (it) viewModel.updateFees(viewModel.enteredAmount.value ?: "")
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

    private val transferFundSuccessObserver = Observer<Boolean> {
        if (it) {
            moveToFundTransferSuccess()
        }
    }
    private val enterAmountObserver = Observer<String> {
        viewModel.updateFees(it)
        when {
            it.isBlank() -> {
                viewModel.state.valid = false
            }
            isBalanceAvailable(it) -> showErrorSnackBar(
                viewModel.state.errorDescription,
                Snackbar.LENGTH_INDEFINITE
            )
            isDailyLimitReached() -> {
                viewModel.state.amountBackground =
                    requireContext().resources.getDrawable(
                        co.yap.yapcore.R.drawable.bg_funds_error,
                        null
                    )
                viewModel.state.valid = false
                showErrorSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_INDEFINITE)
            }
            it.isNotBlank() && viewModel.enteredAmount.value?.toDoubleOrNull() ?: 0.0 < viewModel.state.minLimit || viewModel.enteredAmount.value?.toDoubleOrNull() ?: 0.0 > viewModel.state.maxLimit -> {
                setUpperLowerLimitError()
                viewModel.state.amountBackground =
                    requireContext().resources.getDrawable(
                        co.yap.yapcore.R.drawable.bg_funds_error,
                        null
                    )
                viewModel.state.valid = false
                showErrorSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_INDEFINITE)
            }
            else -> {
                viewModel.state.amountBackground =
                    requireContext().resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
                viewModel.state.valid = true
                cancelAllSnackBar()
            }
        }
    }

    private fun isBalanceAvailable(enteredAmount: String?): Boolean {
        return viewModel.state.checkValidity(enteredAmount ?: "").isNotBlank()
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                when {
                    isOtpRequired() -> {
                        startOtpFragment()
                    }
                    else -> {
                        viewModel.proceedToTransferAmount()
                    }
                }
            }
        }
    }

    private fun setUpperLowerLimitError() {
        viewModel.state.errorDescription = Translator.getString(
            requireContext(),
            Strings.common_display_text_min_max_limit_error_transaction,
            viewModel.state.minLimit.toString().toFormattedCurrency() ?: "",
            viewModel.state.maxLimit.toString()
        )
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
                    amount = viewModel.enteredAmount.value,
                    logoData = LogoData(
                        imageUrl = viewModel.state.imageUrl,
                        position =  args.position
                    )
                )
            )
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
                viewModel.proceedToTransferAmount()
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

        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.enteredAmount.value?.toDoubleOrNull()?.let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        viewModel.state.errorDescription = Translator.getString(
                            requireContext(),
                            Strings.common_display_text_daily_limit_error
                        ).format(remainingDailyLimit.toString().toFormattedCurrency())
                        return enteredAmount > remainingDailyLimit
                    } ?: return false
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun isOtpRequired(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.totalDebitAmountY2Y?.let { totalY2YConsumedAmount ->
                viewModel.enteredAmount.value?.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimitY2Y?.minus(totalY2YConsumedAmount)
                    return enteredAmount >= (remainingOtpLimit ?: 0.0)
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
                viewModel.state.fullName,viewModel.state.imageUrl,
                "AED",
                viewModel.enteredAmount.value ?: "", args.position
            )
        findNavController().navigate(action)
    }

    private fun showErrorSnackBar(errorMessage: String, length: Int) {
        getSnackBarFromQueue(0)?.let {
            if (it.isShown) {
                it.updateSnackBarText(errorMessage)
            }
        } ?: showSnackBar(
            msg = viewModel.state.errorDescription,
            viewBgColor = R.color.errorLightBackground, gravity = Gravity.TOP,
            colorOfMessage = R.color.error, duration = length
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        cancelAllSnackBar()
        viewModel.enteredAmount.removeObservers(this)
        super.onDestroy()

    }

    override fun getBinding(): FragmentY2yFundsTransferBinding {
        return viewDataBinding as FragmentY2yFundsTransferBinding
    }

    override fun onBackPressed(): Boolean {
        viewModel.parentViewModel?.state?.rightButtonVisibility = View.VISIBLE
        return super.onBackPressed()
    }

}