package co.yap.app.modules.forgotpasscode.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.forgotpasscode.viewmodels.CreateNewPasscodeViewModel
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.yapcore.BaseBindingFragment

class CreateNewPasscodeFragment : BaseBindingFragment<ICreatePasscode.ViewModel>() {
    private val args: CreateNewPasscodeFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_create_new_passcode
    override val viewModel: ICreatePasscode.ViewModel
        get() = ViewModelProviders.of(this).get(CreateNewPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.mobileNumber=args.mobileNumber
        viewModel.nextButtonPressEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_createNewPasscodeFragment_to_forgotPasscodeSuccessFragment)
        })
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }
}