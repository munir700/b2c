package co.yap.modules.onboarding.states

import android.app.Application
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.yapcore.BaseState
import java.util.regex.Pattern

class EmailState(application: Application) : BaseState(), IEmail.State {
    val mContext = application.applicationContext

    @get:Bindable
    override var emailHint: String =
        application.applicationContext.getString(R.string.screen_enter_email_display_text_email_address)
        get() = field
        set(value) {
            field = value

            notifyPropertyChanged(BR.emailHint)
        }

    @get:Bindable
    override var email: String = ""
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    @get:Bindable
    override var emailError: String = ""
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailError)
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
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)


        }
        get() {
            return field
        }

    @get:Bindable
    override var cursorPlacement: Boolean = true
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.cursorPlacement)

        }

    @get:Bindable
    override var setSelection: Int = email.length
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.setSelection)

        }

    @get:Bindable
    override var refreshField: Boolean = false
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshField)

        }
    @get:Bindable
    override var handleBackPress: Int = 0
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.handleBackPress)
        }

    fun isValidEmail(email: String): Boolean {
        var inputStr: CharSequence = ""
        var isValid = false
        val expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        // with plus       String expression = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        inputStr = email
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        if (matcher.matches()) {
            isValid = true
        }

        return isValid
    }

    fun validateEmail(email: String): Boolean {
        var isValidEmail = false
        if ("" == email.trim { it <= ' ' }) {
            isValidEmail = false
        } else if (isValidEmail(email)) {
            isValidEmail = true
        } else {
            return isValidEmail
        }
        return isValidEmail
    }


    private fun setSuccessUI() {
        refreshField = true
        valid = true
        emailError = ""
        drawbleRight = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.path)
    }

    private fun setErrorUI(): Boolean {
        /* disable core button
                 set error UI*/
        valid = false
        refreshField = false
        emailError = mContext.getString(R.string.screen_phone_number_display_text_error)
        return false
    }

    private fun setDefaultUI() {
        refreshField = true
        valid = false
        drawbleRight = null
    }


    fun getTextWatcher(): TextWatcher {

        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (!p0.isNullOrEmpty() && p0.length >= 5) {
                    if (validateEmail(p0.toString())) {
                        setSuccessUI()
                    } else {
                        setDefaultUI()
                    }
                }
            }
        }
    }
}