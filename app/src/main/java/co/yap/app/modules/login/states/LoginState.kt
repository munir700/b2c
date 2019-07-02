package co.yap.app.modules.login.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.yapcore.BaseState

class LoginState : BaseState(), ILogin.State {

    @get:Bindable
    override var email: String = "abs"
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
            valid=validate()
        }

    @get:Bindable
    override var emailError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailError)
            valid=validate()
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyChange()
        }

    fun validate(): Boolean {
        return (email.length > 5)
    }
}