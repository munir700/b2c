package co.yap.modules.forgotpasscode.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.modules.forgotpasscode.viewmodels.ForgotPasscodeOtpViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R

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
                        args!!.mobileNumber
                    )
                findNavController().navigate(action)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.nextButtonPressEvent.removeObservers(this)
    }
}