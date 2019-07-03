package co.yap.app.modules.login.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.yapcore.BaseState

class LoginState : BaseState(), ILogin.State {

    @get:Bindable
    override var email: String = "wkm@yap.com"
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var emailError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailError)
            notifyPropertyChanged(BR.valid)
        }


    @get:Bindable
    override var valid: Boolean = false
        get() = validate()


    fun validate(): Boolean {
        return (email.length > 5 && emailError.isEmpty())
    }
}