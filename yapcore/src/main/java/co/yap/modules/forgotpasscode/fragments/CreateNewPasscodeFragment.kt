package co.yap.modules.forgotpasscode.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.forgotpasscode.interfaces.ICreatePasscode
import co.yap.modules.forgotpasscode.viewmodels.CreateNewPasscodeViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentCreateNewPasscodeBinding
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.preventTakeScreenshot

class CreateNewPasscodeFragment : BaseBindingFragment<ICreatePasscode.ViewModel>() {
    private val args: CreateNewPasscodeFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_create_new_passcode
    override val viewModel: ICreatePasscode.ViewModel
        get() = ViewModelProviders.of(this).get(CreateNewPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preventTakeScreenshot()
        viewModel.mobileNumber = args.mobileNumber
        viewModel.nextButtonPressEvent.observe(this, Observer {
            if (it == R.id.tvTermsAndConditions) {
                Utils.openWebPage("sajdlasjdlasjdl", "", activity)
            } else {
                val action =
                    CreateNewPasscodeFragmentDirections.actionCreateNewPasscodeFragmentToForgotPasscodeSuccessFragment(
                        navigationType = args.navigationType
                    )
                findNavController().navigate(action)
//            findNavController().navigate(R.id.action_createNewPasscodeFragment_to_forgotPasscodeSuccessFragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBindings().dialer.hideFingerprintView()
        getBindings().dialer.upDatedDialerPad(viewModel.state.passcode)
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    fun getBindings(): FragmentCreateNewPasscodeBinding {
        return viewDataBinding as FragmentCreateNewPasscodeBinding
    }
}