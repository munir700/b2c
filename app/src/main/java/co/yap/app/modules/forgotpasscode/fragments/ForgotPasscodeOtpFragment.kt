package co.yap.app.modules.forgotpasscode.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.app.modules.forgotpasscode.viewmodels.ForgotPasscodeOtpViewModel
import co.yap.yapcore.BaseBindingFragment

class ForgotPasscodeOtpFragment : BaseBindingFragment<IForgotPasscodeOtp.ViewModel>(), IForgotPasscodeOtp.View {
    private val args: ForgotPasscodeOtpFragmentArgs by navArgs()
    override val viewModel: IForgotPasscodeOtp.ViewModel
        get() = ViewModelProviders.of(this).get(ForgotPasscodeOtpViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_forgot_passcode_otp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        //    viewModel.mobileNumber=args.mobileNumber
        viewModel.state.mobileNumber[0] = args.mobileNumber
        viewModel.destination=args.username
        viewModel.emailOtp=args.emailOtp
    }
 /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        //viewModel.mobileNumber= arguments?.let { ForgotPasscodeOtpFragmentArgs.fromBundle(it).mobileNumber } as String

    }*/

    override fun setObservers() {

        viewModel.nextButtonPressEvent.observe(this, Observer {
            val action=ForgotPasscodeOtpFragmentDirections.actionForgotPasscodeFragmentToCreateNewPasscodeFragment(args.username)
            findNavController().navigate(action)

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.nextButtonPressEvent.removeObservers(this)
    }
}