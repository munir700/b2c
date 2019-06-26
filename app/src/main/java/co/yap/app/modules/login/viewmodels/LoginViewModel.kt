package co.yap.app.modules.login.viewmodels

import android.app.Application
import co.yap.app.api.login.LoginRepository
import co.yap.app.api.login.requestdtos.LoginRequest
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.states.LoginState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.yapcore.BaseViewModel

class LoginViewModel(application: Application) : BaseViewModel<ILogin.State>(application), ILogin.ViewModel, IRepositoryHolder<LoginRepository> {

    override val state: ILogin.State
        get() = LoginState()

    override fun performLogin(email: String, password: String) {
        launch {
            repository.login(LoginRequest("", ""))
        }
    }

    override val repository: LoginRepository
        get() = LoginRepository
}