package co.yap.app.modules.login.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.states.LoginState
import co.yap.app.modules.login.viewmodels.LoginViewModel
import co.yap.yapcore.BaseBindingActivity

class LogInActivity : BaseBindingActivity<ILogin.ViewModel>(),ILogin.View {

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LogInActivity::class.java)
            return intent
        }
    }

    override fun getBindingVariable(): Int =BR.loginViewModel
    override fun getLayoutId(): Int =R.layout.screen_log_in

    override val viewModel: ILogin.ViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (viewModel.state as LoginState).addOnPropertyChangedCallback(object:Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                if (propertyId == BR.email) {
////                    viewModel.state.email
//                }
//            }
//        })

    }

}
