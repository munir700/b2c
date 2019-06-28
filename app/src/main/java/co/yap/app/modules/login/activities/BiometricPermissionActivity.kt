package co.yap.app.modules.login.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.app.R
import co.yap.app.modules.login.interfaces.IBiometricPermission
import co.yap.app.modules.login.viewmodels.BiometricPermissionViewModel
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.screen_biometric_permission.*

class BiometricPermissionActivity : BaseBindingActivity<IBiometricPermission.ViewModel>(), IBiometricPermission.View {
    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.screen_biometric_permission

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, BiometricPermissionActivity::class.java);
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
    }

    override val viewModel: IBiometricPermission.ViewModel
        get() = ViewModelProviders.of(this).get(BiometricPermissionViewModel::class.java)

    private fun initListeners() {
        tvTermsAndConditions.setOnClickListener {

        }

        btnTouchId.setOnClickListener {

        }

        tvNoThanks.setOnClickListener {

        }
    }


}
