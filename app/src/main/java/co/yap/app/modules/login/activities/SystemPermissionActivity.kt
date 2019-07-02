package co.yap.app.modules.login.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.constants.Constants.SCREEN_TYPE
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.app.modules.login.viewmodels.SystemPermissionViewModel
import co.yap.yapcore.BaseBindingActivity

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SystemPermissionActivity : BaseBindingActivity<ISystemPermission.ViewModel>(), ISystemPermission.View {
    companion object {
        fun newIntent(context: Context, type: String): Intent {
            val intent = Intent(context, SystemPermissionActivity::class.java);
            intent.putExtra(SCREEN_TYPE, type)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.systemPermissionViewModel

    override fun getLayoutId(): Int = R.layout.screen_biometric_permission

    override val viewModel: ISystemPermission.ViewModel
        get() = ViewModelProviders.of(this).get(SystemPermissionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.screenType = getScreenType()
        viewModel.registerLifecycleOwner(this)
    }

    private fun getScreenType(): String {
        return intent.getStringExtra(SCREEN_TYPE)
    }

}
