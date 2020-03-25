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
import androidx.navigation.fragment.navArgs
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.LogoData
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.webview.WebViewFragment
import co.yap.sendMoney.activities.BeneficiaryCashTransferActivity
import co.yap.sendMoney.addbeneficiary.interfaces.IInternationalTransactionConfirmation
import co.yap.sendMoney.addbeneficiary.viewmodels.InternationalTransactionConfirmationViewModel
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentInternationalTransactionConfirmationBinding
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.URL_DISCLAIMER_TERMS
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager

class InternationalTransactionConfirmationFragment :
    BaseBindingFragment<IInternationalTransactionConfirmation.ViewModel>(),
    IInternationalTransactionConfirmation.View {
    val args: InternationalTransactionConfirmationFragmentArgs by navArgs()
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_international_transaction_confirmation

    override val viewModel: IInternationalTransactionConfirmation.ViewModel
        get() = ViewModelProviders.of(this)
            .get(InternationalTransactionConfirmationViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context is BeneficiaryCashTransferActivity) {
            viewModel.beneficiary =
                (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        if (activity is BeneficiaryCashTransferActivity) {
            setData()
            setDisclaimerText()
            (activity as BeneficiaryCashTransferActivity).viewModel.state.toolBarVisibility = true
            (activity as BeneficiaryCashTransferActivity).viewModel.state.rightButtonVisibility =
                false
            (activity as BeneficiaryCashTransferActivity).viewModel.state.leftButtonVisibility =
                true
            (activity as BeneficiaryCashTransferActivity).viewModel.state.toolBarTitle =
                viewModel.state.confirmHeading

        }
    }

    override fun setData() {
        viewModel.state.args = args
        viewModel.state.name = args.beneficiaryName
        viewModel.state.confirmHeading =
            getString(Strings.screen_cash_pickup_funds_display_otp_header)
        viewModel.state.referenceNumber = args.referenceNumber
        viewModel.state.beneficiaryCountry = args.country


        viewModel.state.transferDescription = resources.getText(
            getString(Strings.screen_funds_confirmation_success_description)
            ,
            requireContext().color(R.color.colorPrimaryDark, args.senderCurrency)
            , requireContext().color(
                R.color.colorPrimaryDark,
                args.fxRateAmount.toFormattedCurrency() ?: ""
            ),
            // viewModel.state.name
            args.firstName
            ,
            requireContext().color(
                R.color.colorPrimaryDark,
                "${args.fromFxRate} to ${args.toFxRate}"
            )
        )

        viewModel.state.position = args.position

        viewModel.state.receivingAmountDescription =
            resources.getText(
                getString(Strings.screen_funds_receive_description)
                ,
                //viewModel.state.name
                args.firstName,
                requireContext().color(
                    R.color.colorPrimaryDark,
                    "${args.receiverCurrencyAmount.toFormattedCurrency()} ${args.toFxRateCurrency}"
                )
            )

        viewModel.state.transferFeeDescription =
            resources.getText(
                getString(Strings.screen_funds_transfer_fee_description), requireContext().color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${args.totalAmount.toFormattedCurrency()}"
                )
            )
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.isOtpRequired.observe(this, Observer {
            if (it)
                startOtpFragment()
        })
    }

    private fun startOtpFragment() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    otpAction = viewModel.state.args?.otpAction,
                    mobileNumber = MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(
                        requireContext()
                    ),
                    amount = args.fxRateAmount,
                    username = viewModel.beneficiary?.fullName(),
                    emailOtp = false,
                    logoData = LogoData(
                        imageUrl = viewModel.beneficiary?.beneficiaryPictureUrl,
                        position = viewModel.state.position,
                        flagVisibility = true,
                        beneficiaryCountry = viewModel.state.beneficiaryCountry
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

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                viewModel.requestForTransfer()
            }

            Constants.ADD_SUCCESS -> {
                // Send Broadcast for updating transactions list in `Home Fragment`
                val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                viewModel.state.referenceNumber?.let { referenceNumber ->
                    viewModel.state.position?.let { position ->
                        viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                            val action =
                                InternationalTransactionConfirmationFragmentDirections.actionInternationalTransactionConfirmationFragmentToTransferSuccessFragment2(
                                    "",
                                    args.senderCurrency,
                                    args.fxRateAmount.toFormattedCurrency() ?: "",
                                    referenceNumber,
                                    position,
                                    beneficiaryCountry,
                                    viewModel.state.cutOffTimeMsg ?: ""
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }

    private fun setDisclaimerText() {
        val myClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startFragment(
                    fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                        Constants.PAGE_URL to URL_DISCLAIMER_TERMS
                    ), showToolBar = false
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
        getBinding().tvDisclaimer.text = spanStr
        getBinding().tvDisclaimer.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onResume() {
        setObservers()
        super.onResume()
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()
    }

    fun getBinding(): FragmentInternationalTransactionConfirmationBinding {
        return viewDataBinding as FragmentInternationalTransactionConfirmationBinding
    }

}
