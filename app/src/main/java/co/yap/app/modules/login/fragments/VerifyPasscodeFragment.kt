package co.yap.app.modules.login.fragments

import android.app.Activity
import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.view.View
import androidx.annotation.Keep
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.app.R
import co.yap.app.activities.MainActivity
import co.yap.app.constants.Constants
import co.yap.yapcore.helpers.encryption.EncryptionUtils
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.viewmodels.VerifyPasscodeViewModel
import co.yap.household.onboarding.OnboardingHouseHoldActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.others.helper.Constants.REQUEST_CODE
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.widgets.NumberKeyboardListener
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.biometric.BiometricCallback
import co.yap.yapcore.helpers.biometric.BiometricManagerX
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_verify_passcode.*

class VerifyPasscodeFragment : BaseBindingFragment<IVerifyPasscode.ViewModel>(), BiometricCallback,
    IVerifyPasscode.View {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var mBiometricManagerX: BiometricManagerX

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_verify_passcode


    override val viewModel: IVerifyPasscode.ViewModel
        get() = ViewModelProviders.of(this).get(VerifyPasscodeViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preventTakeScreenShot(true)
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.loginSuccess.observe(this, loginSuccessObserver)
        viewModel.validateDeviceResult.observe(this, validateDeviceResultObserver)
        viewModel.accountInfo.observe(this, onFetchAccountInfo)
        viewModel.createOtpResult.observe(this, createOtpObserver)
        setObservers()

        arguments?.let {
            viewModel.state.username = VerifyPasscodeFragmentArgs.fromBundle(it).username
            viewModel.state.verifyPassCodeEnum =
                it.getString(REQUEST_CODE, VerifyPassCodeEnum.ACCESS_ACCOUNT.name)
        }
        dialer.hideFingerprintView()

        sharedPreferenceManager = SharedPreferenceManager(requireContext())
        viewModel.state.deviceId =
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID) as String
        mBiometricManagerX = BiometricManagerX(
            requireContext(), mapOf(
                Pair(
                    FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE,
                    getString(R.string.error_override_hw_unavailable)
                )
            )
        )

        val fingerprintsEnabled = mBiometricManagerX.hasFingerprintEnrolled()
        if (BiometricUtil.isFingerprintSupported && fingerprintsEnabled
            && BiometricUtil.isHardwareSupported(requireActivity())
            && BiometricUtil.isPermissionGranted(requireActivity())
            && BiometricUtil.isFingerprintAvailable(requireActivity())
        ) {

            if (sharedPreferenceManager.getValueBoolien(
                    SharedPreferenceManager.KEY_TOUCH_ID_ENABLED,
                    false
                )
            ) {
                dialer.showFingerprintView()
                showFingerprintDialog()
//                Handler().postDelayed(
//                    {
//                        showFingerprintDialog()
//                    }, 500
//                )
            } else {
                dialer.hideFingerprintView()
            }
        }
        dialer.setNumberKeyboardListener(object : NumberKeyboardListener {
            override fun onNumberClicked(number: Int, text: String) {

            }

            override fun onLeftButtonClicked() {
                showFingerprintDialog()
            }

            override fun onRightButtonClicked() {
            }
        })
//        dialer.onButtonClickListener = View.OnClickListener {
//            if (it.id == R.id.btnFingerPrint)
//                showFingerprintDialog()

        ivBackBtn.setOnClickListener {
            if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY)) {
                activity?.onBackPressed()
            } else {
                doLogout()
            }
        }
    }

    private fun showFingerprintDialog() {
        mBiometricManagerX.showDialog(
            requireActivity(),
            BiometricManagerX.DialogStrings(title = getString(R.string.biometric_title))
        )
    }

    override fun onStart() {
        super.onStart()
        dialer.reset()
    }

    override fun onResume() {
        super.onResume()
        mBiometricManagerX.subscribe(this@VerifyPasscodeFragment)
    }

    override fun onPause() {
        super.onPause()
        viewModel.state.passcode = dialer.getText()
        mBiometricManagerX.unSubscribe()
    }

    override fun setObservers() {
        viewModel.forgotPasscodeButtonPressEvent.observe(this, Observer {
            when (it) {
                R.id.tvForgotPassword -> {
                    if (!sharedPreferenceManager.getValueBoolien(
                            SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                            false
                        )
                    ) {
                        goToNext(viewModel.state.username)
                    } else {
                        sharedPreferenceManager.getUserName()?.let { username ->
                            viewModel.state.username = username
                            goToNext(viewModel.state.username)
                        } ?: toast("Invalid user name")
                    }
                }
            }
        })
    }


    private fun goToNext(name: String) {
        val action =
            VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToForgotPasscodeNavigation(
                name,
                !Utils.isUsernameNumeric(name),
                viewModel.mobileNumber
            )
        findNavController().navigate(action)
    }

    private fun doLogout() {
        MyUserManager.doLogout(requireContext(), true)
        if (activity is MainActivity) {
            (activity as MainActivity).onBackPressedDummy()
        } else {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        viewModel.signInButtonPressEvent.removeObservers(this)
        viewModel.loginSuccess.removeObservers(this)
        viewModel.validateDeviceResult.removeObservers(this)
        viewModel.createOtpResult.removeObservers(this)
        viewModel.forgotPasscodeButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val signInButtonObserver = Observer<Boolean> {
        viewModel.isFingerprintLogin = false
        viewModel.state.passcode = dialer.getText()
        if (!sharedPreferenceManager.getValueBoolien(
                SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            setUsername()
        } else {
            sharedPreferenceManager.getUserName()?.let {
                viewModel.state.username = it
            } ?: updateName()
        }
        viewModel.login()
    }

    private fun updateName() {
        viewModel.state.username = ""
    }

    private val loginSuccessObserver = Observer<Boolean> {
        if (it) {
            if (viewModel.isFingerprintLogin) {
                sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, true)
                navigateToDashboard()
            } else {
                if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY)) {
                    navigateToDashboard()
                } else {
                    viewModel.validateDevice()
                }
            }
        } else {
            dialer.startAnimation()
        }
    }

    private val validateDeviceResultObserver = Observer<Boolean> {
        if (it) {
            navigateToDashboard()
        } else {
            viewModel.createOtp()
        }
    }

    private val onFetchAccountInfo = Observer<AccountInfo> {
        it?.run {
            sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, true)
            if (!sharedPreferenceManager.getValueBoolien(
                    SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                    false
                )
            ) {
                if (BiometricUtil.isFingerprintSupported
                    && BiometricUtil.isHardwareSupported(requireActivity())
                    && BiometricUtil.isPermissionGranted(requireActivity())
                    && BiometricUtil.isFingerprintAvailable(requireActivity())
                ) {
                    val action =
                        VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToSystemPermissionFragment(
                            Constants.TOUCH_ID_SCREEN_TYPE
                        )
                    findNavController().navigate(action)
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                        true
                    )
                } else {
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                        true
                    )
                    val action =
                        VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToSystemPermissionFragment(
                            Constants.NOTIFICATION_SCREEN_TYPE
                        )
                    findNavController().navigate(action)
                }
            } else {
                if (accountType == AccountType.B2C_HOUSEHOLD.name) {
                    SharedPreferenceManager(requireContext()).setThemeValue(co.yap.yapcore.constants.Constants.THEME_HOUSEHOLD)
                    val bundle = Bundle()
                    bundle.putBoolean(OnboardingHouseHoldActivity.EXISTING_USER, false)
                    bundle.putParcelable(OnboardingHouseHoldActivity.USER_INFO, it)
                    startActivity(OnboardingHouseHoldActivity.getIntent(requireContext(), bundle))
                    activity?.finish()
                } else {
                    findNavController().navigate(R.id.action_goto_yapDashboardActivity)
                    activity?.finish()
                }
            }
        }
    }

    private val createOtpObserver = Observer<Boolean> {
        val action =
            VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToPhoneVerificationSignInFragment(
                viewModel.state.username,
                viewModel.state.passcode
            )
        findNavController().navigate(action)
    }

    private fun setUsername() {
        viewModel.state.username =
            arguments?.let { VerifyPasscodeFragmentArgs.fromBundle(it).username } as String
    }

    private fun navigateToDashboard() {
        if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY)) {
            val intent = Intent()
            intent.putExtra("CheckResult", true)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        } else {
            viewModel.getAccountInfo()
        }
    }

    override fun onSdkVersionNotSupported() {
    }

    override fun onBiometricAuthenticationNotSupported() {
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onBiometricAuthenticationNotAvailable() {
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
    }

    override fun onBiometricAuthenticationInternalError(error: String) {
        showToast(error)
    }

    override fun onAuthenticationFailed() {
    }

    override fun onAuthenticationCancelled() {

    }

    override fun onAuthenticationSuccessful() {
        viewModel.isFingerprintLogin = true
        sharedPreferenceManager?.let { sharedPreferenceManager ->
            context?.let { context ->
                val passCode =
                    sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_PASSCODE)
                passCode?.let {
                    val passedCode = EncryptionUtils.decrypt(context, passCode)
                    passedCode?.let { passedCode ->
                        viewModel.state.passcode = passedCode
                        dialer.upDatedDialerPad(viewModel.state.passcode)
                    }
                }

                val userName =
                    sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME)
                userName?.let {
                    val encryptedUserName = EncryptionUtils.decrypt(context, userName)
                    encryptedUserName?.let { encryptedUserName ->
                        viewModel.state.username = encryptedUserName
                    }
                }

                if (!viewModel.state.username.isNullOrEmpty() && !viewModel.state.passcode.isNullOrEmpty())
                    viewModel.login()
            }
        }

    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
    }
}

@Keep
enum class VerifyPassCodeEnum {
    VERIFY,
    ACCESS_ACCOUNT
}

