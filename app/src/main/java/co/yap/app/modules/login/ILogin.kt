package co.yap.app.modules.login

import co.yap.yapcore.IBase

interface ILogin {
    interface View : IBase.View

    interface ViewModel : IBase.ViewModel<State> {
        fun performLogin(email: String, password: String)
    }

    interface State : IBase.State
}