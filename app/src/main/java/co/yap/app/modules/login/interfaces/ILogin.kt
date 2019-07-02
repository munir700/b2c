package co.yap.app.modules.login.interfaces

import co.yap.yapcore.IBase

interface ILogin {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun performLogin(email: String, password: String)
    }

    interface State : IBase.State {
        var email: String
        var emailError: String
        var valid: Boolean
    }
}