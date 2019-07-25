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
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import java.util.regex.Pattern

class LoginViewModel(application: Application) : BaseViewModel<ILogin.State>(application), ILogin.ViewModel,
    IRepositoryHolder<AuthRepository> {
    override val signInButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val signUpButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: LoginState = LoginState()
    override val repository: AuthRepository = AuthRepository
    private val adminRepository: AdminRepository = AdminRepository

    override fun handlePressOnLogin() {
        state.twoWayTextWatcher = verifyUsername(state.twoWayTextWatcher.trim())
        validateUsername()
    }

    override fun handlePressOnSignUp() {
        signUpButtonPressEvent.value = true
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

    private fun validateUsername() {
        launch {
            state.loading = true
            when (val response = adminRepository.verifyUsername(state.twoWayTextWatcher)) {
                is RetroApiResponse.Success -> {
                    signInButtonPressEvent.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    state.error = response.error.message
                }
            }
            state.loading = false
        }
    }

    private fun verifyUsername(enteredUsername: String): String {
        var username = enteredUsername
        if (isUsernameNumeric(username)) {
            if (username.startsWith("+")) {
                username = username.replace("+", "00")
                return username
            } else if (username.startsWith("00")) {
                return username
            } else if (username.startsWith("0")) {
                username = username.substring(1, username.length)
                return username
            } else {
                return username
            }

        } else {
            return username
        }
    }


    fun isUsernameNumeric(username: String): Boolean {
        var inputStr: CharSequence
        var isValid = false
        val expression = "^[0-9+]*\$"

        inputStr = username
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        if (matcher.matches()) {
            isValid = true
        }

        return isValid
    }
}