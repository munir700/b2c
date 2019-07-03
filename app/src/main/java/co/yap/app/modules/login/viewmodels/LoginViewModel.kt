package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.util.Log
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.states.LoginState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel

class LoginViewModel(application: Application) : BaseViewModel<ILogin.State>(application), ILogin.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val state: LoginState = LoginState()

    override val repository: AuthRepository = AuthRepository

    override fun performLogin(email: String, password: String) {
        launch {
            when (val response = repository.login(email, password)) {
                is RetroApiResponse.Success -> {

                    Log.d("AccessToken", response.data.accessToken)
                }
                is RetroApiResponse.Error -> {
                    state.error = response.error.message
                    Log.d("Error", response.error.message)
                }
            }

        }
    }

    override fun handlePressOnLogin() {
        if (state.validate()) {
            performLogin(state.email, "Aaaaaa1" )
        }
    }
}