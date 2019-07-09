package co.yap.app.modules.login.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.constants.Constants.SCREEN_TYPE
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.app.modules.login.viewmodels.SystemPermissionViewModel
import co.yap.modules.onboarding.activities.LiteDashboardActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.SharedPreferenceManager

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SystemPermissionActivity : BaseBindingActivity<ISystemPermission.ViewModel>(), ISystemPermission.View {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    companion object {
        fun newIntent(context: Context, type: String): Intent {
            val intent = Intent(context, SystemPermissionActivity::class.java);
            intent.putExtra(SCREEN_TYPE, type)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.screen_biometric_permission

    override val viewModel: ISystemPermission.ViewModel
        get() = ViewModelProviders.of(this).get(SystemPermissionViewModel::class.java)


    private val permissionGrantedObserver = Observer<Boolean> {
        sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, true)
        startActivity(LiteDashboardActivity.newIntent(this, AccountType.B2C_ACCOUNT))
    }

    private val permissionNotGrantedObserver = Observer<Boolean> {
        sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, false)
        startActivity(LiteDashboardActivity.newIntent(this, AccountType.B2C_ACCOUNT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferenceManager = SharedPreferenceManager(this@SystemPermissionActivity)

        viewModel.screenType = getScreenType()
        viewModel.registerLifecycleOwner(this)

        viewModel.permissionGrantedPressEvent.observe(this, permissionGrantedObserver)
        viewModel.permissionNotGrantedPressEvent.observe(this, permissionNotGrantedObserver)
    }

    private fun getScreenType(): String {
        return intent.getStringExtra(SCREEN_TYPE)
    }

}
