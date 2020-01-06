package co.yap.modules.forgotpasscode.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.forgotpasscode.interfaces.ICreatePasscode
import co.yap.modules.forgotpasscode.viewmodels.CreateNewPasscodeViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.helpers.Utils

class CreateNewPasscodeFragment : BaseBindingFragment<ICreatePasscode.ViewModel>() {
    private val args: CreateNewPasscodeFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_create_new_passcode
    override val viewModel: ICreatePasscode.ViewModel
        get() = ViewModelProviders.of(this).get(CreateNewPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.preventTakeScreenshot(requireActivity())
        viewModel.mobileNumber=args.mobileNumber
        viewModel.nextButtonPressEvent.observe(this, Observer {
            val action=CreateNewPasscodeFragmentDirections.actionCreateNewPasscodeFragmentToForgotPasscodeSuccessFragment(navigationType = args.navigationType)
            findNavController().navigate(action)
//            findNavController().navigate(R.id.action_createNewPasscodeFragment_to_forgotPasscodeSuccessFragment)
        })
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }
}