package co.yap.modules.dashboard.more.profile.states

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.yapcore.BaseState

class ChangeEmailState(application: Application) : BaseState(), IChangeEmail.State {

    val context = application.applicationContext

    @get:Bindable
    override var newEmail: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.newEmail)
        }
    @get:Bindable
    override var newConfirmEMail: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.newConfirmEMail)
        }

    @get:Bindable
    override var errorMessage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMessage)
        }

    @get:Bindable
    override var background: Drawable? = context.getDrawable(R.drawable.bg_edit_text_under_line)
        set(value) {
            field = value
            notifyPropertyChanged(BR.background)
        }
    @get:Bindable
    override var valid: Boolean=true
        set(value) {
            field=value
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var drawableRight: Drawable?=null
        set(value) {
            field=value
            notifyPropertyChanged(BR.drawableRight)
        }

    fun checkEmailValidation():Boolean {
        if (newEmail!=""){
        if (newEmail != newConfirmEMail) {
            errorMessage="Both email should matched."
            background = context.getDrawable(R.drawable.bg_edit_text_red_under_line)
            drawableRight=context.getDrawable(R.drawable.ic_error)
            return false
        }else{
            return true
        }
        }else{
           // valid=false
            return true
        }
    }
}