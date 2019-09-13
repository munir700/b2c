package co.yap.app.modules.login.states

import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.yapcore.BaseState

class LoginState : BaseState(), ILogin.State {

    @get:Bindable
    override var email: String = "ha@ha.co"
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
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }


    fun validate(): Boolean {
        return (email.length > 5 && emailError.isEmpty())
    }

    @get:Bindable
    override var twoWayTextWatcher: String = ""
        set(value) {
            field = value

            notifyPropertyChanged(BR.twoWayTextWatcher)
            setTwoWayTextWatcher()
        }


    @get:Bindable
    override var drawbleRight: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawbleRight)
        }
        get() {
            return field
        }

    @get:Bindable
    override var refreshField: Boolean = false
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshField)
        }


    private fun setTwoWayTextWatcher() {

        if (!twoWayTextWatcher.isEmpty() && twoWayTextWatcher.length >= 6) {
            setSuccessUI()
        } else {
            setDefaultUI()
        }
    }

    private fun setDefaultUI() {
        refreshField = true
        valid = false
        drawbleRight = null
    }

    private fun setSuccessUI() {
        refreshField = true
        valid = true
        emailError = ""
        drawbleRight = null
    }

}