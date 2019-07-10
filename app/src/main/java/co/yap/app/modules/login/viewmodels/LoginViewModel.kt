package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.states.LoginState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class LoginViewModel(application: Application) : BaseViewModel<ILogin.State>(application), ILogin.ViewModel {
    override val signInButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val signUpButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: LoginState = LoginState()

    override fun handlePressOnLogin() {
        signInButtonPressEvent.value = true
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
}