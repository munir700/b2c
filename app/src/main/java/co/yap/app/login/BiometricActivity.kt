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
            .setNegativeButtonText("Cancel")
            .build()


        findViewById<Button>(R.id.btnAuth).setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }
}
