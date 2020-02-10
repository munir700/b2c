package co.yap.modules.dashboard.yapit.y2y.transfer.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.Gravity.CENTER_VERTICAL
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.R
import co.yap.databinding.FragmentY2yFundsTransferBinding
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.modules.dashboard.yapit.y2y.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2y.transfer.viewmodels.Y2YFundsTransferViewModel
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.otp.OtpToolBarData
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.*
import kotlin.math.abs


class Y2YTransferFragment : Y2YBaseFragment<IY2YFundsTransfer.ViewModel>(), IY2YFundsTransfer.View {
    val args: Y2YTransferFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_y2y_funds_transfer

    override val viewModel: Y2YFundsTransferViewModel
        get() = ViewModelProviders.of(this).get(Y2YFundsTransferViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.availableBalance = MyUserManager.cardBalance.value?.availableBalance
        viewModel.getTransactionFee()
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })
        viewModel.transferFundSuccess.observe(this, transferFundSuccessObserver)


    }

    private val transferFundSuccessObserver = Observer<Boolean> {
        if (it) {
            moveToFundTransferSuccess()
        }
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                when {
                    isDailyLimitReached() -> {
                        viewModel.errorEvent.call()
                    }
                    isOtpRequired() -> {
                        createOtp()
                    }
                    else -> {
                        viewModel.proceedToTransferAmount()
                    }
                }
            }
            Constants.CARD_FEE -> {
                viewModel.state.transferFee =
                    resources.getText(
                        getString(Strings.common_text_fee), requireContext().color(
                            R.color.colorPrimaryDark,
                            "${viewModel.state.currencyType} ${Utils.getFormattedCurrency(viewModel.state.fee)}"
                        )
                    )
            }

        }
    }

    private fun createOtp() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    OTPActions.Y2Y.name,
                    MyUserManager.user?.currentCustomer?.getCompletePhone(),
                    MyUserManager.user?.currentCustomer?.getFullName(),
                    false,
                    OtpToolBarData()
                )
            )
        ) { resultCode, data ->
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
            " " + getString(Strings.common_text_currency_type) + " " + Utils.getFormattedCurrency(
                viewModel.state.availableBalance
            )
        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))
        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                if (p0?.length!! > 0) {
                    etAmount.gravity = Gravity.CENTER_HORIZONTAL or CENTER_VERTICAL
                } else {
                    etAmount.gravity = Gravity.CENTER_HORIZONTAL or CENTER_VERTICAL
                }
        })
    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.state.amount.toDoubleOrNull()?.let { enteredAmount ->
                        val remainingDailyLimit = abs(dailyLimit - totalConsumedAmount)
                        viewModel.state.errorDescription =
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
        viewModel.transactionThreshold.value?.let {
            it.totalDebitAmountRemittance?.let { totalSMConsumedAmount ->
                viewModel.state.amount.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimit ?: 0.0 - totalSMConsumedAmount
                    return enteredAmount > remainingOtpLimit
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
                viewModel.state.fullName,
                "AED",
                viewModel.state.amount, args.position
            )
        findNavController().navigate(action)
    }

    private fun showErrorSnackBar() {
        CustomSnackbar.showErrorCustomSnackbar(
            context = requireContext(),
            layout = clFTSnackbar,
            message = viewModel.state.errorDescription
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
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