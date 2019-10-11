package co.yap.modules.dashboard.more.profile.states

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.Utils

class ChangeEmailState(application: Application) : BaseState(), IChangeEmail.State {

    val context = application.applicationContext

    @get:Bindable
    override var newEmail: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.newEmail)
            confirmEmailValidation()
        }
    @get:Bindable
    override var newConfirmEMail: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.newConfirmEMail)
            confirmEmailValidation()
        }

    @get:Bindable
    override var errorMessage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMessage)
        }

    @get:Bindable
    override var backgroundNew: Drawable? = context.getDrawable(R.drawable.bg_edit_text_under_line)
        set(value) {
            field = value
            notifyPropertyChanged(BR.backgroundNew)
        }

    @get:Bindable
    override var backgroundConfirm: Drawable? =
        context.getDrawable(R.drawable.bg_edit_text_under_line)
        set(value) {
            field = value
            notifyPropertyChanged(BR.backgroundConfirm)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var drawableConfirm: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawableConfirm)
        }

    @get:Bindable
    override var drawableNew: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawableNew)
        }

    fun confirmEmailValidation(): Boolean {
        return if (Utils.validateEmail(newEmail)) {
            backgroundNew =
                context.getDrawable(R.drawable.bg_edit_text_under_line)
            drawableNew =
                context.getDrawable(R.drawable.path)
            if (newEmail != newConfirmEMail) {
                backgroundConfirm =
                    context.getDrawable(if (Utils.validateEmail(newConfirmEMail)) R.drawable.bg_edit_text_under_line else R.drawable.bg_edit_text_under_line)
                drawableConfirm = null
                valid = false
                false
            } else {
                valid = true
                backgroundConfirm =
                    context.getDrawable(if (Utils.validateEmail(newConfirmEMail)) R.drawable.bg_edit_text_under_line else R.drawable.bg_edit_text_under_line)
                drawableConfirm =
                    context.getDrawable(if (Utils.validateEmail(newConfirmEMail)) R.drawable.path else R.drawable.path)
                errorMessage = ""
                true
            }
        } else {
            backgroundNew =
                context.getDrawable(R.drawable.bg_edit_text_under_line)
            drawableNew = null

            backgroundConfirm =
                context.getDrawable(R.drawable.bg_edit_text_under_line)
            drawableConfirm = null
            valid = false
            false
        }
    }


}