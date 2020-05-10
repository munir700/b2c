package co.yap.modules.dashboard.more.changepasscode.fragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.passcode.IPassCode
import co.yap.modules.passcode.PassCodeViewModel
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentPassCodeBinding
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.startFragmentForResult

class UpdateNewPasscodeFragment : ChangePasscodeBaseFragment<IPassCode.ViewModel>(),
    IPassCode.View {
    val args: UpdateNewPasscodeFragmentArgs by navArgs()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pass_code
    override val viewModel: IPassCode.ViewModel
        get() = ViewModelProviders.of(this).get(PassCodeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().dialer.upDatedDialerPad(viewModel.state.passCode)
        getBinding().dialer.hideFingerprintView()
        if (activity is MoreActivity) {
            (activity as MoreActivity).viewModel.preventTakeDeviceScreenShot.value = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setTitles(
            title = getString(Strings.screen_set_passcode_display_text_heading),
            buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
        )
        setObservers()

    }

    fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    val action =
                        UpdateNewPasscodeFragmentDirections.actionUpdateNewPasscodeFragmentToUpdateConfirmPasscodeFragment(
                            token = args.token,
                            newPinCode = viewModel.state.passCode
                        )
                    findNavController().navigate(action)
                }
                R.id.tvForgotPasscode -> {
                    val sharedPreferenceManager = SharedPreferenceManager(requireContext())
                    startOtpFragment(sharedPreferenceManager.getDecryptedUserName() ?: "")
                }
            }
        })
    }

    private fun startOtpFragment(name: String) {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    otpAction = OTPActions.FORGOT_PASS_CODE.name,
                    username = name,
                    emailOtp = !Utils.isUsernameNumeric(name)
                )
            )
        ) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                val token =
                    data?.getValue(
                        "token",
                        ExtraType.STRING.name
                    ) as? String

                viewModel.mobileNumber = (data?.getValue(
                    "mobile",
                    ExtraType.STRING.name
                ) as? String) ?: ""

                token?.let {
                    viewModel.token = it
                        navigateToForgotPassCodeFlow()
                }
            }
        }
    }

    private fun navigateToForgotPassCodeFlow() {
        val sharedPreferenceManager = SharedPreferenceManager(requireContext())
        if (viewModel.isUserLoggedIn()) {
            sharedPreferenceManager.getDecryptedUserName()?.let {
                val action =
                    UpdateNewPasscodeFragmentDirections.actionUpdateNewPasscodeFragmentToForgotPasscodeNavigation(
                        viewModel.mobileNumber,
                        viewModel.token,
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