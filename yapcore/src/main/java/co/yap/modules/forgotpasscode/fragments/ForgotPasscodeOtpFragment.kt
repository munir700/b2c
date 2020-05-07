package co.yap.modules.forgotpasscode.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.forgotpasscode.activities.ForgotPasscodeActivity
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.modules.forgotpasscode.viewmodels.ForgotPasscodeOtpViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot

open class ForgotPasscodeOtpFragment : BaseBindingFragment<IForgotPasscodeOtp.ViewModel>(),
    IForgotPasscodeOtp.View {

    private val args: ForgotPasscodeOtpFragmentArgs? by navArgs()
    override val viewModel: IForgotPasscodeOtp.ViewModel
        get() = ViewModelProviders.of(this).get(ForgotPasscodeOtpViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_forgot_passcode_otp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.reverseTimer(10, requireContext())
        if (activity is ForgotPasscodeActivity) {
            (activity as ForgotPasscodeActivity).preventTakeDeviceScreenShot.value = false
        } else {
            preventTakeScreenShot(false)
        }

    }

    override fun loadData() {
        if (args?.mobileNumber!!.startsWith("00")) {
            viewModel.state.mobileNumber[0] =
                args!!.mobileNumber.replaceRange(
                    0,
                    2,
                    "+"
                )
        } else {
            viewModel.state.mobileNumber[0] = args?.mobileNumber
        }
        viewModel.destination = args!!.username
        // viewModel.destination=args.mobileNumber
        viewModel.emailOtp = args!!.emailOtp
    }

    override fun setObservers() {
        viewModel.nextButtonPressEvent.observe(this, Observer {
            val action =
                ForgotPasscodeOtpFragmentDirections.actionForgotPasscodeFragmentToCreateNewPasscodeFragment(
                    args?.mobileNumber ?: "", viewModel.token ?: "", args?.navigationType ?: ""
                )
            findNavController().navigate(action)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.nextButtonPressEvent.removeObservers(this)
    }
}