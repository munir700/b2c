package co.yap.sendMoney.addbeneficiary.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import co.yap.sendMoney.R
import co.yap.databinding.FragmentCashTransferConfirmationBinding
import co.yap.sendMoney.activities.BeneficiaryCashTransferActivity
import co.yap.sendMoney.addbeneficiary.interfaces.ICashTransferConfirmation
import co.yap.sendMoney.addbeneficiary.viewmodels.CashTransferConfirmationViewModel
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.LogoData
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.webview.WebViewFragment
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.URL_DISCLAIMER_TERMS
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager

class CashTransferConfirmationFragment :
    BaseBindingFragment<ICashTransferConfirmation.ViewModel>(), ICashTransferConfirmation.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_cash_transfer_confirmation

    override val viewModel: CashTransferConfirmationViewModel
        get() = ViewModelProviders.of(this).get(CashTransferConfirmationViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).let { activity ->
                viewModel.beneficiary =
                    activity.viewModel.state.beneficiary
                activity.viewModel.state.leftButtonVisibility = true
                activity.viewModel.state.rightButtonVisibility = false
                activity.viewModel.state.toolBarTitle = "Confirm transfer"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveDataFromArgs()
        setTransferAmountString()
        setTransferFeeAmountString()
        setDisclaimerText()
    }

    private fun receiveDataFromArgs() {
        viewModel.state.enteredAmount.set(arguments?.let {
            CashTransferConfirmationFragmentArgs.fromBundle(
                it
            ).amount
        })
        viewModel.reasonCode =
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).reasonCode }
                .toString()
        viewModel.reason =
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).reason }.toString()
        viewModel.transferNote =
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).transferNote }

        viewModel.state.transferFee.set(
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).transferFee }
                .toString())

        viewModel.state.position.set(
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).position })

    }

    private fun setTransferAmountString() {
        viewModel.state.description.set(
            resources.getText(
                getString(Strings.scren_send_money_funds_transfer_confirmation_display_text_amount_uaefts)
                ,
                //viewModel.state.name
                viewModel.beneficiary?.firstName,
                requireContext().color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${viewModel.state.enteredAmount.get()?.toFormattedCurrency()}"
                )
            )
        )
    }

    private fun setDisclaimerText() {
        val myClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startFragment(
                    fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                        Constants.PAGE_URL to URL_DISCLAIMER_TERMS
                    ), showToolBar = true
                )
            }
        }
        val newValue =
            getString(Strings.scren_send_money_funds_transfer_confirmation_display_text_disclaimer).plus(
                " "
            )
        val clickValue =
            getString(Strings.scren_send_money_funds_transfer_confirmation_display_text_disclaimer_terms)
        val spanStr = SpannableStringBuilder("$newValue $clickValue")
        spanStr.setSpan(
            myClickableSpan,
            (newValue.length + 1),
            (newValue.length + 1) + clickValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanStr.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
            (newValue.length + 1),
            (newValue.length + 1) + clickValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        getViewBinding().tvDisclaimer.text = spanStr
        getViewBinding().tvDisclaimer.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setTransferFeeAmountString() {
        viewModel.state.transferFeeDescription.set(
            resources.getText(
                getString(Strings.scren_send_money_funds_transfer_confirmation_display_text_fee),
                requireContext().color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${viewModel.state.transferFee.get()?.toFormattedCurrency()}"
                )
            )
        )
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                if (isOtpRequired()) {
                    startOtpFragment()
                } else {
                    viewModel.proceedToTransferAmount()
                }
            }
            Constants.ADD_CASH_PICK_UP_SUCCESS -> {
                cashTransferSuccess()
            }
        }
    }

    private fun cashTransferSuccess() {
        // Send Broadcast for updating transactions list in `Home Fragment`
        val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        viewModel.state.referenceNumber.let {
            val action =
                CashTransferConfirmationFragmentDirections.actionCashTransferConfirmationFragmentToTransferSuccessFragment2(
                    "",
                    "AED",
                    viewModel.state.enteredAmount.get()?.toFormattedCurrency() ?: "",
                    viewModel.state.referenceNumber.get().toString(),
                    viewModel.state.position.get() ?: 0,
                    viewModel.state.cutOffTimeMsg.get() ?: ""
                )
            findNavController().navigate(action)
        }
    }

    private fun isOtpRequired(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.totalDebitAmountRemittance?.let { totalSMConsumedAmount ->
                viewModel.state.enteredAmount.get()?.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimit?.minus(totalSMConsumedAmount)
                    return enteredAmount >= (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun startOtpFragment() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    otpAction = getOtpAction(),
                    mobileNumber = MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(
                        requireContext()
                    ),
                    amount = viewModel.state.enteredAmount.get(),
                    username = viewModel.beneficiary?.fullName(),
                    emailOtp = false,
                    logoData = LogoData(
                        imageUrl = viewModel.beneficiary?.beneficiaryPictureUrl,
                        position = viewModel.state.position.get()
                    )
                )
            ),
            showToolBar = true,
            toolBarTitle = getString(Strings.screen_cash_pickup_funds_display_otp_header)
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
                viewModel.proceedToTransferAmount()
            }
        }
    }

    private fun getOtpAction(): String? {
        viewModel.beneficiary?.beneficiaryType?.let { type ->
            return (when (type) {
                SendMoneyBeneficiaryType.DOMESTIC.type -> OTPActions.DOMESTIC_TRANSFER.name
                SendMoneyBeneficiaryType.UAEFTS.type -> OTPActions.UAEFTS.name
                else -> null
            })
        } ?: return null
    }

    override fun getViewBinding(): FragmentCashTransferConfirmationBinding {
        return (viewDataBinding as FragmentCashTransferConfirmationBinding)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}