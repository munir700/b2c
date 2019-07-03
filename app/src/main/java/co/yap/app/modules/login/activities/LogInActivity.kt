package co.yap.app.modules.login.activities

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.viewmodels.LoginViewModel
import co.yap.yapcore.BaseBindingActivity

class LogInActivity : BaseBindingActivity<ILogin.ViewModel>(), ILogin.View {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, LogInActivity::class.java)
    }

    override fun getBindingVariable(): Int = BR.loginViewModel
    override fun getLayoutId(): Int = R.layout.screen_log_in

    override val viewModel: ILogin.ViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)

}
