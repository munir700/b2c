package co.yap.app.modules.login.interfaces

import android.widget.TextView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface ILogin {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val signInButtonPressEvent: SingleLiveEvent<Boolean>
        val signUpButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnLogin()
        fun handlePressOnSignUp()
        fun onEditorActionListener(): TextView.OnEditorActionListener
    }

    interface State : IBase.State {
        var email: String
        var emailError: String
        var valid: Boolean
        var twoWayTextWatcher: String
    }
}