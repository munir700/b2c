package co.yap.app.modules.forgotpasscode.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.forgotpasscode.interfaces.IForgotPasscodeSuccess
import co.yap.app.modules.forgotpasscode.viewmodels.ForgotPasscodeSuccessViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

class ForgotPasscodeSuccessFragment : BaseBindingFragment<IForgotPasscodeSuccess.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_forgot_passcode_success

    override val viewModel: IForgotPasscodeSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(ForgotPasscodeSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.handlePressOnButtonEvent.observe(this, Observer {
            findNavController().popBackStack(R.id.loginFragment,false)
        })
    }
}