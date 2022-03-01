package com.yap.yappakistan.ui.auth.forgotpasscode.verifyforgototp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yap.core.base.BaseNavFragment
import com.yap.core.extensions.hideKeyboard
import com.yap.core.extensions.observe
import com.yap.core.extensions.observeEvent
import com.yap.core.utils.MySMSBroadcastReceiver
import com.yap.core.utils.SingleEvent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.yap.uikit.utils.getFormattedPhoneNumber
import com.yap.uikit.widgets.otptextview.OTPListener
import com.yap.uikit.widgets.toolbarview.ToolBarView
import com.yap.yappakistan.BR
import com.yap.yappakistan.R
import com.yap.yappakistan.databinding.FragmentVerifyForgotOtpBinding
import com.yap.yappakistan.networking.microservices.messages.requestdtos.ForgotPasscodeOtpRequest
import com.yap.yappakistan.ui.auth.loginpasscode.LoginPasscodeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyForgotOTPFragment :
    BaseNavFragment<FragmentVerifyForgotOtpBinding, IVerifyForgotOTP.State, IVerifyForgotOTP.ViewModel>(
        R.layout.fragment_verify_forgot_otp
    ), IVerifyForgotOTP.View, MySMSBroadcastReceiver.OnSmsReceiveListener, OTPListener,
    ToolBarView.OnToolBarViewClickListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: IVerifyForgotOTP.ViewModel by viewModels<VerifyForgotOTPVM>()
    private var intentFilter: IntentFilter? = null
    private var appSMSBroadcastReceiver: MySMSBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFragmentArguments()
        initBroadCastForSMS()
        mViewBinding.tbView.setOnToolBarViewClickListener(this)
        mViewBinding.otpView.otpListener = this
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnVerifyOtp -> onOTPVerifyClick()
            R.id.btnResend -> onResendOTPClick()
        }
    }

    private fun initBroadCastForSMS() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        appSMSBroadcastReceiver = MySMSBroadcastReceiver(this)
    }

    override fun onOTPComplete(otp: String) {
        viewModel.otp = otp
        viewModel.viewState.isValid.value = true
        onOTPVerifyClick()
    }

    override fun onOTPVerifyClick() {
        val request = ForgotPasscodeOtpRequest(
            destination = viewModel.mobileNumber.replace(" ", ""),
            emailOTP = false,
            otp = viewModel.otp
        )
        viewModel.verifyOtp(request)
    }

    override fun onReceive(code: Intent?) {
        code?.let {
            it.resolveActivity(requireContext().packageManager)?.run {
            }
        }
    }

    override fun onInteractionListener() {
        viewModel.viewState.isValid.value = false
    }

    override fun getFragmentArguments() {
        arguments?.let {
            viewModel.mobileNumber = it.getString(LoginPasscodeFragment::class.java.name) ?: ""
            viewModel.viewState.formattedNumber.value = getFormattedPhoneNumber(
                requireContext(),
                viewModel.mobileNumber
            )
        }
    }

    override fun onStartIconClicked() {
        if (mViewBinding.otpView.hideKeyboard())
             hideKeyboard()
       else
            requireActivity().onBackPressed()

    }

    override fun onResendOTPClick() {
        val request = ForgotPasscodeOtpRequest(
            destination = viewModel.mobileNumber.replace(" ", ""),
            emailOTP = false
        )
        viewModel.resendOtp(request)
    }

    private fun handleForgotOtpCreation(isForgotOtpCreated: Boolean) {
        if (isForgotOtpCreated) {
            viewModel.reverseTimer(10)
        }
    }

    private fun handleForgotOtpVerification(isForgotOtpVerified: Boolean) {
        if (isForgotOtpVerified) {
            viewModel.openCreateNewPasscodeScreen()
        }
    }

    private fun openCreateNewPasscodeScreen(navigateEvent: SingleEvent<Int>) {
        navigateEvent.getContentIfNotHandled()?.let { destinationId ->
            val bundle = Bundle()
            bundle.putString("mobileNumber", viewModel.mobileNumber)
            bundle.putString("otpToken", viewModel.optToken)
            navigateWithPopup(destinationId, R.id.verifyForgotOTPFragment, bundle)
        }
    }

    override fun viewModelObservers() {
        observe(viewModel.isForgotOtpCreated, ::handleForgotOtpCreation)
        observe(viewModel.isForgotOtpVerified, ::handleForgotOtpVerification)
        observeEvent(viewModel.openCreateNewPasscode, ::openCreateNewPasscodeScreen)
    }
}