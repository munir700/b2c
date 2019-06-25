package co.yap.app.login

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import co.yap.app.R
import java.util.concurrent.Executors

class BiometricActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric)

        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.e("onAuthenticationError", "onAuthenticationError")

                when (errorCode) {
                    BiometricPrompt.ERROR_NEGATIVE_BUTTON -> print("onAuthenticationError: ERROR_NEGATIVE_BUTTON")
                    BiometricPrompt.ERROR_HW_NOT_PRESENT -> print("onAuthenticationError: ERROR_HW_NOT_PRESENT")
                    BiometricPrompt.ERROR_HW_UNAVAILABLE -> print("onAuthenticationError: ERROR_HW_UNAVAILABLE")
                    BiometricPrompt.ERROR_NO_BIOMETRICS -> print("onAuthenticationError: ERROR_NO_BIOMETRICS")
                    BiometricPrompt.ERROR_NO_SPACE -> print("onAuthenticationError: ERROR_NO_SPACE")
                    else -> print("Something went wrong")
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.e("onAuthSucceeded", "onAuthenticationSucceeded")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.e("onAuthenticationFailed", "onAuthenticationFailed")
            }
        })


        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Sign in")
            .setNegativeButtonText("Use Passcode Instead")
            .build()


        findViewById<Button>(R.id.btnAuth).setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }
}
