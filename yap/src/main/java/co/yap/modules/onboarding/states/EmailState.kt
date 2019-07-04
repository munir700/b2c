package co.yap.modules.onboarding.states

import android.app.Application
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.yapcore.BaseState

class EmailState(application: Application) : BaseState(), IEmail.State {
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

    val mContext = application.applicationContext
    var PHONE_NUMBER_LENGTH: Int = 16

    var twoDigitStr: String = ""
    var threeDigitStr: String = ""

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

    fun checkMobileNumberValidation(phoneNumber: String): Boolean? {
        if (!phoneNumber.trim().equals("")) {
            val input = phoneNumber.trim().replace("+", "")
            val regex = "[0-9]+"
            if (input.length < 5 || !input.matches(regex.toRegex())) {

                /* disable core input field
                 set error UI*/
                valid = false
                refreshField = false
                emailError = mContext.getString(R.string.screen_phone_number_display_text_error)
                return false
            }
        }
        if (phoneNumber.trim().equals("")) {

            /* disable core button
             set error UI*/
            refreshField = false
            valid = false
            drawbleRight = null
            emailError = mContext.getString(R.string.screen_phone_number_display_text_error)
            return false
        }
        refreshField = true
        valid = true
        emailError = ""
        drawbleRight = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.path)

        return true
    }


    fun getTextWatcher(): TextWatcher {

        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (!p0.isNullOrEmpty() && p0.length >= 5) {
                    if (p0.toString().length == PHONE_NUMBER_LENGTH) {
                        refreshField = true
                        var phoneNumber: String = p0.toString().trim()
                        phoneNumber = phoneNumber.trim().replace(" ", "")

                        checkMobileNumberValidation(phoneNumber)
                    } else {
                        refreshField = true
                        valid = false
                        drawbleRight = null
                    }

                    if (p0.toString().toCharArray().size == 7 && p1 == 6) {

                        var charArray = p0.toString().toCharArray()
                        if (charArray.get(charArray.lastIndex).isDigit()) {

                            twoDigitStr = p0.toString().substring(5, 7)
                            val builder = SpannableStringBuilder()
//                            val colored = countryCode


                            builder.append(twoDigitStr)
                            builder.append(" ")
                            setSelection = builder.toString().length
                        }
                    }
                    if (p0.toString().toCharArray().size == 11 && p1 == 10) {

                        var charArray = p0.toString().toCharArray()
                        if (charArray.get(charArray.lastIndex).isDigit()) {

                            threeDigitStr = p0.toString().substring(7)
                            val builder = SpannableStringBuilder()

//                            builder.append(
//                                colored
//                            )

                            builder.append(twoDigitStr)
                            builder.append(threeDigitStr)
                            setSelection = builder.toString().length
                        }
                    }

                    if (p0.toString().length == 5) {
                        /*disable backpress*/

                        handleBackPress = p1
                    } else {
                        /*enable backpress*/

                        handleBackPress = p1
                    }
                }
            }
        }
    }
}