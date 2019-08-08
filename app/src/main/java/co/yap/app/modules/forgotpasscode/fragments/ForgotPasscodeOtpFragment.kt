package co.yap.app.modules.forgotpasscode.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.app.modules.forgotpasscode.viewmodels.ForgotPasscodeOtpViewModel
import co.yap.yapcore.BaseBindingFragment

class ForgotPasscodeOtpFragment : BaseBindingFragment<IForgotPasscodeOtp.ViewModel>() {
    override val viewModel: IForgotPasscodeOtp.ViewModel
        get() = ViewModelProviders.of(this).get(ForgotPasscodeOtpViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_forgot_passcode_otp


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }

    private fun setObservers() {

        viewModel.nextButtonPressEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_forgotPasscodeFragment_to_createNewPasscodeFragment)

        })
    }

    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }
}