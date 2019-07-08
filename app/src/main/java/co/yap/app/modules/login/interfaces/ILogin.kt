package co.yap.app.modules.login.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface ILogin {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val signInButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnLogin()
    }

    interface State : IBase.State {
        var email: String
        var emailError: String
        var valid: Boolean
        var twoWayTextWatcher: String
    }
}