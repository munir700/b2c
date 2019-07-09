package co.yap.app.modules.login.viewmodels

import android.app.Application
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.states.LoginState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class LoginViewModel(application: Application) : BaseViewModel<ILogin.State>(application), ILogin.ViewModel {
    override val signInButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: LoginState = LoginState()

    override fun handlePressOnLogin() {
        signInButtonPressEvent.value = true
    }
}