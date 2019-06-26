package co.yap.app.modules.login.activities

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import co.yap.app.R
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.viewmodels.LoginViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IBase

class LogInActivity : BaseBindingActivity<ILogin.ViewModel>(),ILogin.View {

    override fun getBindingVariable(): Int =0
    override fun getLayoutId(): Int =R.layout.activity_log_in

    override val viewModel: ILogin.ViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)
}
