package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
                    // TODO: Remove these values and handle the response as per your need
                    state.toast = "Login Successful"
                    Log.d("Login", "AccessToken: " + response.data.accessToken)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }

        }
    }

    override fun handlePressOnLogin() {
        if (state.validate()) {
            performLogin(state.email, "Aaaaaa1@")
        }
    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handlePressOnLogin()
                }
                return false
            }
        }
    }
}