package co.yap.app.modules.login.activities

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import co.yap.app.R
import co.yap.app.modules.login.interfaces.IBiometricPermission
import co.yap.app.modules.login.viewmodels.BiometricPermissionViewModel
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.screen_biometric_permission.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BiometricPermissionActivity : BaseBindingActivity<IBiometricPermission.ViewModel>(), IBiometricPermission.View {
    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.screen_biometric_permission

    companion object {
        private val SCREEN_TYPE = "screenType"
        fun newIntent(context: Context, type: String): Intent {
            val intent = Intent(context, BiometricPermissionActivity::class.java);
            intent.putExtra(SCREEN_TYPE, type)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkFingerPrint()
        viewModel.screenType = getScreenType()
        initViews()
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

    fun getScreenType(): String {
        return intent.getStringExtra(SCREEN_TYPE)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun initViews() {
        val drawable:Drawable=applicationContext.getDrawable(viewModel.setViews().icon)
        ivFingerprint.setImageDrawable(drawable)
        tvTermsAndConditions.isVisible=viewModel.setViews().termsAndConditionsVisibility
        tvTermsAndConditionsTitle.isVisible=viewModel.setViews().termsAndConditionsVisibility
        tvTouchIdPermissionTitle.text=viewModel.setViews().title
        btnTouchId.text=viewModel.setViews().buttonTitle

    }
}
