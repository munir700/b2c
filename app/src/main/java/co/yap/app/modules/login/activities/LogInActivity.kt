package co.yap.app.modules.login.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.viewmodels.LoginViewModel
import co.yap.app.modules.startup.activities.AccountSelectionActivity
import co.yap.yapcore.BaseBindingActivity

// TODO: Remove this file once all references are invalid
@Deprecated("Use LoginFragment instead")
class LogInActivity : BaseBindingActivity<ILogin.ViewModel>(), ILogin.View {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, LogInActivity::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_log_in

    override val viewModel: ILogin.ViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
        viewModel.signUpButtonPressEvent.observe(this, signUpButtonObserver)
    }

    override fun onDestroy() {
        viewModel.signInButtonPressEvent.removeObservers(this)
        viewModel.signUpButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    private val signInButtonObserver = Observer<Boolean> {
        if (it) {
            //startActivity(VerifyPasscodeActivity.newIntent(this, viewModel.state.twoWayTextWatcher))
        } else {
            viewModel.state.emailError = "That's not the right username. Please try again"
        }
    }

    private val signUpButtonObserver = Observer<Boolean> {
        startActivity(Intent(this, AccountSelectionActivity::class.java))
        finish()
    }

}
