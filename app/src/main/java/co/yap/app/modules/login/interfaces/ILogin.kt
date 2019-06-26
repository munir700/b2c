package co.yap.app.modules.login

import co.yap.yapcore.IBase

interface ILogin {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun performLogin(email: String, password: String)
    }

    interface State : IBase.State
}