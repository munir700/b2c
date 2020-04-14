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
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.viewmodels.VerifyPasscodeViewModel
import co.yap.household.dashboard.main.HouseholdDashboardActivity
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.others.helper.Constants.REQUEST_CODE
import co.yap.widgets.NumberKeyboardListener
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.constants.Constants.KEY_IS_FINGERPRINT_PERMISSION_SHOWN
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED
import co.yap.yapcore.constants.Constants.VERIFY_PASS_CODE_BTN_TEXT
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.biometric.BiometricCallback
import co.yap.yapcore.helpers.biometric.BiometricManagerX
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.launchActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferenceManager = SharedPreferenceManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preventTakeScreenShot(true)
        addObservers()
        dialer.hideFingerprintView()
        receiveData()
        updateUUID()
        bioMetricLogic()
        onbackPressLogic()
    }

    private fun addObservers() {
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.loginSuccess.observe(this, loginSuccessObserver)
        viewModel.validateDeviceResult.observe(this, validateDeviceResultObserver)
        MyUserManager.isUserAccountInfo?.observe(this, onFetchAccountInfo)
        viewModel.createOtpResult.observe(this, createOtpObserver)
        MyUserManager.switchProfile.observe(this, switchProfileObserver)
        setObservers()
    }

    private fun receiveData() {
        arguments?.let { it ->
            viewModel.state.username = VerifyPasscodeFragmentArgs.fromBundle(it).username
            if (VerifyPasscodeFragmentArgs.fromBundle(it).isAccountBlocked) {
                viewModel.showAccountBlockedError()
            }

            it.getString(VERIFY_PASS_CODE_BTN_TEXT)?.let {
                viewModel.state.btnVerifyPassCodeText = it
            }
            viewModel.state.verifyPassCodeEnum =
                it.getString(REQUEST_CODE, VerifyPassCodeEnum.ACCESS_ACCOUNT.name)
        }
    }

    private fun updateUUID() {
        sharedPreferenceManager.getValueString(KEY_APP_UUID)?.let {
            viewModel.state.deviceId = it
        } ?: toast("Invalid UUID found")
    }

    private fun bioMetricLogic() {
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
                    KEY_TOUCH_ID_ENABLED,
                    false
                ) && sharedPreferenceManager.getDecryptedPassCode() != null
            ) {

                dialer.showFingerprintView()
                showFingerprintDialog()
            } else {
                dialer.hideFingerprintView()
            }
        }
        dialer.setNumberKeyboardListener(object : NumberKeyboardListener {
            override fun onLeftButtonClicked() {
                showFingerprintDialog()
            }
        })
    }

    private fun onbackPressLogic() {
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
                    if (!isUserLoginIn()) {
                        goToNext(viewModel.state.username)
                    } else {
                        sharedPreferenceManager.getDecryptedUserName()?.let { username ->
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
        MyUserManager.isUserAccountInfo?.removeObserver(onFetchAccountInfo)
        super.onDestroyView()
    }

    private fun isUserLoginIn(): Boolean {
        return sharedPreferenceManager.getValueBoolien(
            KEY_IS_USER_LOGGED_IN,
            false
        )
    }

    private val signInButtonObserver = Observer<Boolean> {
        viewModel.isFingerprintLogin = false
        viewModel.state.passcode = dialer.getText()
        if (!isUserLoginIn()) {
            setUsername()
        } else {
            sharedPreferenceManager.getDecryptedUserName()?.let {
                viewModel.state.username = it
            } ?: updateName()
        }
        if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY))
            viewModel.verifyPasscode()
        else
            viewModel.login()
    }

    private fun updateName() {
        if (isUserLoginIn()) {
            viewModel.state.username = MyUserManager.user?.currentCustomer?.email ?: ""
            return
        }

        viewModel.state.username = ""
    }

    private val loginSuccessObserver = Observer<Boolean> {
        if (it) {
            if (viewModel.isFingerprintLogin) {
                sharedPreferenceManager.save(KEY_IS_USER_LOGGED_IN, true)
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

    private val onFetchAccountInfo = Observer<Boolean> {
        if (it) {
            //            setUserAttributes()
            sharedPreferenceManager.save(KEY_IS_USER_LOGGED_IN, true)
            if (!sharedPreferenceManager.getValueBoolien(
                    KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
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
                        KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                        true
                    )
                } else {
                    sharedPreferenceManager.save(
                        KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                        true
                    )
                    val action =
                        VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToSystemPermissionFragment(
                            Constants.NOTIFICATION_SCREEN_TYPE
                        )
                    findNavController().navigate(action)
                }
            } else {
                if (MyUserManager.shouldGoToHousehold()) {
                    MyUserManager.switchProfile()
                } else {
                    gotoYapDashboard()
                }
            }
        }
    }

    private val switchProfileObserver = Observer<Boolean> {
        if (it) {
            if (MyUserManager.isOnBoarded()) {
                if (MyUserManager.isDefaultUserYap()) {
                    gotoYapDashboard()
                } else {
                    launchActivity<HouseholdDashboardActivity>(clearPrevious = true)
                }
            } else {
                launchActivity<OnBoardingHouseHoldActivity>(clearPrevious = true) {
                    putExtra(OnBoardingHouseHoldActivity.USER_INFO, MyUserManager.user)
                }
            }
        }
    }

    private fun gotoYapDashboard() {
        findNavController().navigate(R.id.action_goto_yapDashboardActivity)
        activity?.finish()
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
            MyUserManager.getAccountInfo()
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

        sharedPreferenceManager.getDecryptedPassCode()?.let { passedCode ->
            viewModel.state.passcode = passedCode
            dialer.upDatedDialerPad(viewModel.state.passcode)
        }

        sharedPreferenceManager.getDecryptedUserName()?.let { encryptedUserName ->
            viewModel.state.username = encryptedUserName
        }

        if (!viewModel.state.username.isNullOrEmpty() && !viewModel.state.passcode.isNullOrEmpty()) {
            if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY))
                viewModel.verifyPasscode()
            else
                viewModel.login()
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
