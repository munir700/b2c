package co.yap.modules.forgotpasscode.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.forgotpasscode.activities.ForgotPasscodeActivity
import co.yap.modules.forgotpasscode.interfaces.ICreatePasscode
import co.yap.modules.forgotpasscode.viewmodels.CreateNewPasscodeViewModel
import co.yap.modules.webview.WebViewFragment
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.PAGE_URL
import co.yap.yapcore.databinding.FragmentCreateNewPasscodeBinding
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot
import co.yap.yapcore.helpers.extentions.startFragment

class CreateNewPasscodeFragment : BaseBindingFragment<ICreatePasscode.ViewModel>() {
    private val args: CreateNewPasscodeFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_create_new_passcode
    override val viewModel: CreateNewPasscodeViewModel
        get() = ViewModelProviders.of(this).get(CreateNewPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.mobileNumber = args.mobileNumber
        viewModel.token = args.token
        viewModel.nextButtonPressEvent.observe(this, Observer {
            if (it == R.id.tvTermsAndConditions) {
                startFragment(
                    fragmentName = WebViewFragment::class.java.name,
                    bundle = bundleOf(PAGE_URL to Constants.URL_TERMS_CONDITION),
                    showToolBar = true
                )
                // Utils.openWebPage(Constants.URL_TERMS_CONDITION, "", activity)
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
        if (activity is ForgotPasscodeActivity) {
            (activity as ForgotPasscodeActivity).preventTakeDeviceScreenShot.value = true
        } else {
            preventTakeScreenShot(true)
        }
    }


    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    fun getBindings(): FragmentCreateNewPasscodeBinding {
        return viewDataBinding as FragmentCreateNewPasscodeBinding
    }

}