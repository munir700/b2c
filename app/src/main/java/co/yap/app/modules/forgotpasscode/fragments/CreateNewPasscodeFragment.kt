package co.yap.app.modules.forgotpasscode.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.forgotpasscode.viewmodels.CreateNewPasscodeViewModel
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.yapcore.BaseBindingFragment

class CreateNewPasscodeFragment:BaseBindingFragment<ICreatePasscode.ViewModel>() {
    override fun getBindingVariable(): Int= BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_create_new_passcode

    override val viewModel: ICreatePasscode.ViewModel
        get() = ViewModelProviders.of(this).get(CreateNewPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, Observer {
        viewModel.state.toast="success"

        })
        //findNavController().navigate(R.id.action_forgotPasscodeFragment_to_createNewPasscodeFragment)
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }
}