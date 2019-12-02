package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.app.R
import co.yap.app.activities.MainActivity
import co.yap.app.constants.Constants
import co.yap.app.login.EncryptionUtils
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.viewmodels.VerifyPasscodeViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.biometric.BiometricCallback
import co.yap.yapcore.helpers.biometric.BiometricManager
import co.yap.yapcore.helpers.biometric.BiometricUtil
import kotlinx.android.synthetic.main.fragment_verify_passcode.*


class VerifyPasscodeFragment : BaseBindingFragment<IVerifyPasscode.ViewModel>(), BiometricCallback,
    IVerifyPasscode.View {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var mBiometricManager: BiometricManager

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_verify_passcode


    override val viewModel: IVerifyPasscode.ViewModel
        get() = ViewModelProviders.of(this).get(VerifyPasscodeViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.loginSuccess.observe(this, loginSuccessObserver)
        viewModel.validateDeviceResult.observe(this, validateDeviceResultObserver)
        viewModel.createOtpResult.observe(this, createOtpObserver)
        setObservers()
        setUsername()
        dialer.hideFingerprintView()
        sharedPreferenceManager = SharedPreferenceManager(context as MainActivity)
        viewModel.state.deviceId =
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID) as String

        if (BiometricUtil.isFingerprintSupported
            && BiometricUtil.isHardwareSupported(context as MainActivity)
            && BiometricUtil.isPermissionGranted(context as MainActivity)
            && BiometricUtil.isFingerprintAvailable(context as MainActivity)
        ) {

            if (sharedPreferenceManager.getValueBoolien(
                    SharedPreferenceManager.KEY_TOUCH_ID_ENABLED,
                    false
                )
            ) {
                dialer.showFingerprintView()
                Handler().postDelayed(
                    {
                        showFingerprintDialog()
                    }, 500
                )
            } else {
                dialer.hideFingerprintView()
            }
        }

        dialer.onButtonClickListener = View.OnClickListener {
            if (it.id == R.id.btnFingerPrint)
                showFingerprintDialog()
        }
    }

    override fun setObservers() {
        viewModel.forgotPasscodeButtonPressEvent.observe(this, Observer {
            when (it) {
                R.id.tvForgotPassword -> {
                    if (sharedPreferenceManager.getValueBoolien(
                            SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                            false
                        )
                    ) {
                        viewModel.state.username = EncryptionUtils.decrypt(
                            context as MainActivity,
                            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
                        ) as String
                    }

                    val action =
                        VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToForgotPasscodeNavigation(
                            viewModel.state.username,
                            viewModel.emailOtp,
                            viewModel.mobileNumber
                        )
                    findNavController().navigate(action)
                }
            }
        })
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
            if (null != sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME)) {
                viewModel.state.username = EncryptionUtils.decrypt(
                    context as MainActivity,
                    sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
                ) as String
            } else {
                viewModel.state.username = ""
            }
        }
        viewModel.login()
    }

    private val loginSuccessObserver = Observer<Boolean> {

        if (it) {
            if (viewModel.isFingerprintLogin) {
                sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, true)
                navigateToDashboard()
            } else {
                viewModel.validateDevice()
            }
        } else {
            dialer.startAnimation()
        }
    }

    private val validateDeviceResultObserver = Observer<Boolean> {
        if (it) {
            sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, true)
            if (!sharedPreferenceManager.getValueBoolien(
                    SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                    false
                )
            ) {
                if (BiometricUtil.isFingerprintSupported
                    && BiometricUtil.isHardwareSupported(context as MainActivity)
                    && BiometricUtil.isPermissionGranted(context as MainActivity)
                    && BiometricUtil.isFingerprintAvailable(context as MainActivity)
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
                navigateToDashboard()
            }
        } else {
            viewModel.createOtp()
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


    private fun showFingerprintDialog() {
        mBiometricManager = BiometricManager.BiometricBuilder(context as MainActivity)
            .setTitle(getString(R.string.biometric_title))
            .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
            .build()
        mBiometricManager.authenticate(this@VerifyPasscodeFragment)


    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.action_goto_yapDashboardActivity)
        activity?.finish()
    }

    override fun onSdkVersionNotSupported() {
    }

    override fun onBiometricAuthenticationNotSupported() {
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

    // crashlytics crash  VerifyPasscodeFragment.kt line 35

    // Never produced so we assumed replacing "context as MainActivity" activity!!.applicationContext in following block

    override fun onAuthenticationSuccessful() {
        viewModel.isFingerprintLogin = true
        EncryptionUtils.decrypt(
            requireContext(),
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_PASSCODE) as String
        )?.let {
            viewModel.state.passcode = it
        }

        EncryptionUtils.decrypt(
            requireContext(),
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
        )?.let { viewModel.state.username = it }

        viewModel.login()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
    }


}

