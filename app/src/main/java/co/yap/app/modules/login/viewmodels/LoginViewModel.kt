package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.states.LoginState
import co.yap.networking.admin.AdminRepository
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.Utils
import java.util.regex.Pattern

class LoginViewModel(application: Application) : BaseViewModel<ILogin.State>(application), ILogin.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val signInButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val signUpButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: LoginState = LoginState()
    override val repository: AuthRepository = AuthRepository
    private val adminRepository: AdminRepository = AdminRepository

    override fun handlePressOnLogin() {
        state.twoWayTextWatcher = Utils.verifyUsername(state.twoWayTextWatcher.trim())
        validateUsername()
    }

    override fun handlePressOnSignUp() {
        signUpButtonPressEvent.value = true
    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handlePressOnLogin()
            }
            false
        }
    }

    private fun validateUsername() {
        launch {
            state.loading = true
            when (val response = adminRepository.verifyUsername(state.twoWayTextWatcher)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data) {
                        signInButtonPressEvent.postValue(true)
                    } else {
                        state.emailError.value = getString(Strings.screen_sign_in_display_text_error_text)
                    }
                }
                is RetroApiResponse.Error -> {
                    state.error = response.error.message
                    println("")
                }
            }
            state.loading = false
        }
    }
}