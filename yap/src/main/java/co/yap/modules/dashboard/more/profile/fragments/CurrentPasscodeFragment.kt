package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.passcode.IPassCode
import co.yap.modules.passcode.PassCodeViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentPassCodeBinding
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils

open class CurrentPasscodeFragment : BaseBindingFragment<IPassCode.ViewModel>(), IPassCode.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pass_code

    override val viewModel: IPassCode.ViewModel
        get() = ViewModelProviders.of(this).get(PassCodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.setTitles(
            title = getString(Strings.screen_current_passcode_display_text_heading),
            buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
        )
        if (context is MoreActivity)
            (context as MoreActivity).goneToolbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().dialer.upDatedDialerPad(viewModel.state.passCode)
        getBinding().dialer.hideFingerprintView()
        if (activity is MoreActivity) {
            (activity as MoreActivity).viewModel.preventTakeDeviceScreenShot.value = true
        }
    }

    fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    viewModel.validatePassCode { isValidPassCode ->
                        if (isValidPassCode)
                            findNavController().navigate(R.id.action_currentPasscodeFragment_to_updateNewPasscodeFragment)
                        else
                            getBinding().dialer.startAnimation()
                    }
                }
                R.id.tvForgotPasscode -> {
                    val sharedPreferenceManager = SharedPreferenceManager(requireContext())
                    viewModel.forgotPassCodeOtpRequest({
                        navigateToForgotPassCodeFlow()
                    }, sharedPreferenceManager.getDecryptedUserName())
                }
            }
        })
    }

    private fun navigateToForgotPassCodeFlow() {
        val sharedPreferenceManager = SharedPreferenceManager(requireContext())
        if (viewModel.isUserLoggedIn()) {
            sharedPreferenceManager.getDecryptedUserName()?.let {
                val action =
                    CurrentPasscodeFragmentDirections.actionCurrentPasscodeFragmentToForgotPasscodeNavigation(
                        it,
                        !Utils.isUsernameNumeric(it),
                        viewModel.mobileNumber,
                        Constants.FORGOT_PASSCODE_FROM_CHANGE_PASSCODE
                    )
                findNavController().navigate(action)
            } ?: showToast("Invalid username found")
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    fun getBinding(): FragmentPassCodeBinding {
        return viewDataBinding as FragmentPassCodeBinding
    }
}