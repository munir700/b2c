package co.yap.app.modules.login.interfaces

import android.widget.TextView
import co.yap.yapcore.IBase

interface ILogin {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun performLogin(email: String, password: String)
        fun handlePressOnLogin()
        fun onEditorActionListener(): TextView.OnEditorActionListener
    }

    interface State : IBase.State {
        var email: String
        var emailError: String
        var valid: Boolean
    }
}