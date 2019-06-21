package co.yap.app.modules.login

import android.app.Application
import co.yap.app.api.login.LoginRepository
import co.yap.app.api.login.requestdtos.LoginRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.yapcore.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel<ILogin.State>(application), ILogin.ViewModel, IRepositoryHolder<LoginRepository> {

    override val state: ILogin.State
        get() = LoginState()

    override fun performLogin(email: String, password: String) {
        // launch(repository.login(LoginRequest("", "")))
        viewModelScope.launch {
            repository.login(LoginRequest("", ""))
        }
    }

    override val repository: LoginRepository
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}