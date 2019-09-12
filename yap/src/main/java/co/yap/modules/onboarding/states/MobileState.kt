package co.yap.modules.onboarding.states

import android.app.Application
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.widgets.mobile.CountryCodePicker
import co.yap.yapcore.BaseState

class MobileState(application: Application) : BaseState(), IMobile.State {

    val VISIBLE: Int = 0x00000000
    val GONE: Int = 0x00000008
    val mContext = application.applicationContext
    var countryCode: String = "+971 "


    @get:Bindable
    override var background: Drawable? = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.bg_round_edit_text)
        set(value) {
            field = value
            notifyPropertyChanged(BR.background)
        }

    @get:Bindable
    override var drawbleRight: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawbleRight)

        }

    @get:Bindable
    override var mobile: String = "123"
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile)
            if (mobile.length<9){
                mobileNoLength=11

            }

        }

    @get:Bindable
    override var mobileError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileError)
            setErrorResponseLayout()

        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var activeFieldValue: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.activeFieldValue)
        }

    @get:Bindable
    override var errorVisibility: Int = VISIBLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.handleBackPress)
        }

    @get:Bindable
    override var mobileNoLength: Int = 11
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileNoLength)
        }

    @get:Bindable
    var etMobileNumber: EditText? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.etMobileNumber)
            findKeyBoardFocus()
            registerCarrierEditText()
        }

    private fun registerCarrierEditText() {

        val ccpLoadNumber: CountryCodePicker? = CountryCodePicker(mContext)
        ccpLoadNumber!!.registerCarrierNumberEditText(this.etMobileNumber!!)

        ccpLoadNumber.setPhoneNumberValidityChangeListener(object :
            CountryCodePicker.PhoneNumberValidityChangeListener {
            override fun onValidityChanged(isValidNumber: Boolean) {
                if (isValidNumber) {
                    mobileNoLength=11
                    if (mobile.length == 11) {
                        setSuccessUI()
                        drawbleRight = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.path)
                        valid = true

                    } else {
                        setSuccessUI()
                    }
                } else {
//                    mobileNoLength=9
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

    private fun findKeyBoardFocus() {
        etMobileNumber!!.getViewTreeObserver().addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (etMobileNumber!!.isFocused()) {
                        if (!keyboardShown(etMobileNumber!!.getRootView())) {
                            activeFieldValue = false
                        } else {
                            activeFieldValue = true
                        }
                    }
                    return
                }
            })
    }

    fun keyboardShown(rootView: View): Boolean {
        val softKeyboardHeight = 100
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff = rootView.bottom - r.bottom
        return heightDiff > softKeyboardHeight * dm.density
    }

    private fun setErrorLayout() {
        mobileNoLength=9
        valid = false

    }

    private fun setErrorResponseLayout() {
        if (!mobileError.isNullOrEmpty()) {

            drawbleRight = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.invalid_name)
            background = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.bg_round_error_layout)
             errorVisibility = VISIBLE
            valid = false
        }
    }

    private fun setSuccessUI() {
        drawbleRight = null
        background = mContext!!.resources.getDrawable(co.yap.yapcore.R.drawable.bg_round_edit_text)
        activeFieldValue = true
        mobileError = ""
        valid = false

    }
}