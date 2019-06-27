package co.yap.app.login

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.yap.app.R
import co.yap.yapcore.helpers.SharedPreferenceManager

class BiometricActivity : AppCompatActivity(), BiometricCallback {
    lateinit var mBiometricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric)

        findViewById<Button>(R.id.btnAuth).setOnClickListener {
            mBiometricManager = BiometricManager.BiometricBuilder(this@BiometricActivity)
                .setTitle(getString(R.string.biometric_title))
                .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                .build()

            mBiometricManager.authenticate(this@BiometricActivity)
        }

        val sharedPreferenceManager: SharedPreferenceManager = SharedPreferenceManager(this@BiometricActivity)
        val passcode = "5550"
        sharedPreferenceManager.save("encryptedPasscode", EncryptionUtils.encrypt(this@BiometricActivity, passcode)!!)
        val decryptedPasscode = EncryptionUtils.decrypt(
            this@BiometricActivity,
            sharedPreferenceManager.getValueString("encryptedPasscode")!!
        )



        Toast.makeText(
            applicationContext,
            "Plain text: " + passcode + "\n\n " +
                    "Encrypted text: " + sharedPreferenceManager.getValueString("encryptedPasscode")!! + "\n\n " +
                    "Decrypted text: " + decryptedPasscode, Toast.LENGTH_LONG
        ).show()


    }


    override fun onSdkVersionNotSupported() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG)
            .show()
    }

    override fun onBiometricAuthenticationNotSupported() {
        Toast.makeText(
            applicationContext,
            getString(R.string.biometric_error_hardware_not_supported),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onBiometricAuthenticationNotAvailable() {
        Toast.makeText(
            applicationContext,
            getString(R.string.biometric_error_fingerprint_not_available),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(
            applicationContext,
            getString(R.string.biometric_error_permission_not_granted),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onBiometricAuthenticationInternalError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        //        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationCancelled() {
        Toast.makeText(applicationContext, getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show()
        mBiometricManager.cancelAuthentication()
    }

    override fun onAuthenticationSuccessful() {
        Toast.makeText(applicationContext, getString(R.string.biometric_success), Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
        //        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        //        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }
}
