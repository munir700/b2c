package co.yap.modules.onboarding.states

import android.app.Application
import android.graphics.drawable.Drawable
import android.widget.EditText
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.widgets.mobile.CountryCodePicker
import co.yap.yapcore.BaseState

class MobileState(application: Application) : BaseState(), IMobile.State {

    val VISIBLE :Int = 0x00000000
    val GONE :Int = 0x00000008

    val mContext = application.applicationContext
    var countryCode: String = "+971 "


    @get:Bindable
    override var background: Drawable? = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.bg_round_edit_text)
        set(value) {
            field = value
            notifyPropertyChanged(BR.background)
        }
        get() {
            return field
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
    override var activeFieldValue: Boolean = true
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.activeFieldValue)
        }

    @get:Bindable
    override var errorVisibility: Int = VISIBLE
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.handleBackPress)
        }

    @get:Bindable
    var etMobileNumber: EditText? = null
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.etMobileNumber)
            etMobileNumber!!.requestFocus()
            registerCarrierEditText()
            etMobileNumber!!.requestFocus()

        }

    private fun registerCarrierEditText() {

        val ccpLoadNumber: CountryCodePicker? = CountryCodePicker(mContext)
        ccpLoadNumber!!.registerCarrierNumberEditText(this!!.etMobileNumber!!)

        ccpLoadNumber!!.setPhoneNumberValidityChangeListener(object :
            CountryCodePicker.PhoneNumberValidityChangeListener {
            override fun onValidityChanged(isValidNumber: Boolean) {
                if (isValidNumber) {

                    if (mobile.length == 11) {
                        setSuccessUI()
                        drawbleRight = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.path)
                        valid = true

                    } else {
                        setSuccessUI()
                    }
                } else {
                    setSuccessUI()
                    if (mobile.toString().replace(" ", "").trim().length >= 9) {
                        setErrorLayout()

                    } else {
                        setSuccessUI()

                    }

                }
            }
        })
    }

    private fun setErrorLayout() {
        drawbleRight = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.invalid_name)
        background = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.bg_round_error_layout)
        mobileError = mContext.getString(R.string.screen_phone_number_display_text_error)
        errorVisibility = VISIBLE
        valid = false

    }

    private fun setSuccessUI() {
        drawbleRight = null
        background = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.bg_round_edit_text)
        activeFieldValue = true
        mobileError = ""
//        errorVisibility = GONE
        valid = false

    }
}