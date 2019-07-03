package co.yap.modules.onboarding.states

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.yapcore.BaseState

class MobileState(application: Application) : BaseState(), IMobile.State {
    val mContext = application.applicationContext
    var countryCode: String = "+971 "
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
    override var mobile: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile)

        }
        get() {
            return field
        }
    @get:Bindable
    override var mobileError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileError)

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
    override var setSelection: Int = countryCode.length
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

    @SuppressLint("ResourceAsColor")
    fun formatecountry(): SpannableStringBuilder {
        val builder = SpannableStringBuilder(countryCode)
        builder.setSpan(
            ForegroundColorSpan(R.color.greySoft),
            0,
            countryCode.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSelection = countryCode.length

        return builder
    }

    @get:Bindable
    override var inputText: SpannableStringBuilder = formatecountry()
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.inputText)

        }

    @get:Bindable
    override var handleBackPress: Int = 0
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.handleBackPress)
        }


    @SuppressLint("ResourceAsColor")
    private fun setColoredString(builder: SpannableStringBuilder, colored: String) {
        builder.setSpan(
            ForegroundColorSpan(R.color.greySoft),
            0,
            colored.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
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
                mobileError = mContext.getString(R.string.screen_phone_number_display_text_error)
                return false
            }
        }
        if (phoneNumber.trim().equals("")) {

            /* disable core button
             set error UI*/
            refreshField = false
            valid = false
            drawbleRight = null
            mobileError = mContext.getString(R.string.screen_phone_number_display_text_error)
            return false
        }
        refreshField = true
        valid = true
        mobileError = ""
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
                            val colored = countryCode
                            builder.append(
                                colored
                            )
                            setColoredString(builder, colored).toString()

                            builder.append(twoDigitStr)
                            builder.append(" ")
                            inputText = builder
                            setSelection = builder.toString().length
                        }
                    }
                    if (p0.toString().toCharArray().size == 11 && p1 == 10) {

                        var charArray = p0.toString().toCharArray()
                        if (charArray.get(charArray.lastIndex).isDigit()) {

                            threeDigitStr = p0.toString().substring(7)
                            val colored = countryCode
                            val builder = SpannableStringBuilder()

                            builder.append(
                                colored
                            )
                            setColoredString(builder, colored).toString()

                            builder.append(twoDigitStr)
                            builder.append(threeDigitStr)
                            inputText = builder.append(" ")
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