package co.yap.app.modules.login.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.login.BiometricCallback
import co.yap.app.login.BiometricManager
import co.yap.app.login.BiometricUtil
import co.yap.app.login.EncryptionUtils
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.viewmodels.VerifyPasscodeViewModel
import co.yap.modules.onboarding.activities.LiteDashboardActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_verify_passcode.*

class VerifyPasscodeActivity : BaseBindingActivity<IVerifyPasscode.ViewModel>(), BiometricCallback {


    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    lateinit var mBiometricManager: BiometricManager

    companion object {

        private val USERNAME = "username"

        fun newIntent(context: Context, username: String): Intent {
            val intent = Intent(context, VerifyPasscodeActivity::class.java)
            intent.putExtra(USERNAME, username)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_verify_passcode

    override val viewModel: IVerifyPasscode.ViewModel
        get() = ViewModelProviders.of(this).get(VerifyPasscodeViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.loginSuccess.observe(this, loginSuccessObserver)
        dialer.hideFingerprintView()
        sharedPreferenceManager = SharedPreferenceManager(this@VerifyPasscodeActivity)
        viewModel.state.deviceId =
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID) as String

        if (BiometricUtil.isFingerprintSupported
            && BiometricUtil.isHardwareSupported(this@VerifyPasscodeActivity)
            && BiometricUtil.isPermissionGranted(this@VerifyPasscodeActivity)
            && BiometricUtil.isFingerprintAvailable(this@VerifyPasscodeActivity)
        ) {

            if (sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, false)) {
                dialer.showFingerprintView()
                showFingerprintDialog()
            } else {
                dialer.hideFingerprintView()
            }

        }


    }


    private val signInButtonObserver = Observer<Boolean> {
        viewModel.state.passcode = dialer.getText()
        setUsername()
        viewModel.login()
    }

    private val loginSuccessObserver = Observer<Boolean> {

        sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, true)
        sharedPreferenceManager.save(
            SharedPreferenceManager.KEY_PASSCODE,
            EncryptionUtils.encrypt(this, viewModel.state.passcode)!!
        )
        sharedPreferenceManager.save(
            SharedPreferenceManager.KEY_USERNAME,
            EncryptionUtils.encrypt(this, viewModel.state.username)!!
        )
        startActivity(LiteDashboardActivity.newIntent(this, AccountType.B2C_ACCOUNT))
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.signInButtonPressEvent.removeObserver(signInButtonObserver)
    }

    private fun setUsername() {
        viewModel.state.username = intent.getSerializableExtra(USERNAME) as String
    }


    private fun showFingerprintDialog() {
        mBiometricManager = BiometricManager.BiometricBuilder(this@VerifyPasscodeActivity)
            .setTitle(getString(R.string.biometric_title))
            .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
            .build()

        mBiometricManager.authenticate(this@VerifyPasscodeActivity)
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
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        Toast.makeText(applicationContext, getString(R.string.biometric_failed), Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationCancelled() {

    }

    override fun onAuthenticationSuccessful() {
        viewModel.state.passcode = EncryptionUtils.decrypt(
            this,
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_PASSCODE) as String
        )!!
        viewModel.state.username = EncryptionUtils.decrypt(
            this,
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
        )!!
        viewModel.login()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
    }
}