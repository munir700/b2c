package co.yap.modules.dashboard.store.household.onboarding.states

import android.app.Application
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserContact
import co.yap.widgets.mobile.CountryCodePicker
import co.yap.yapcore.BaseState

class HouseHoldUserContactState(application: Application) : BaseState(),
    IHouseHoldUserContact.State {
    val context = application.applicationContext

    @get:Bindable
    override var mobileNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileNumber)

        }

    @get:Bindable
    override var confirmMobileNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmMobileNumber)
        }

    @get:Bindable
    override var background: Drawable? = context.getDrawable(R.drawable.bg_plain_edit_text)
        set(value) {
            field = value
            notifyPropertyChanged(BR.background)
        }

    @get:Bindable
    override var backgroundConfirmMobile: Drawable? = context.getDrawable(R.drawable.bg_plain_edit_text)
        set(value) {
            field = value
            notifyPropertyChanged(BR.backgroundConfirmMobile)
        }


    @get:Bindable
    override var drawbleRight: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawbleRight)

        }

    @get:Bindable
    override var drawbleRightConfirmMobile: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawbleRightConfirmMobile)

        }

    @get:Bindable
    override var countryCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.countryCode)
        }

    @get:Bindable
    override var mobile: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile)
            if (mobile.length < 9) {
                mobileNoLength = 11
            }
            errorMessage = ""

        }

    @get:Bindable
    override var etMobileNumber: EditText? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.etMobileNumber)
            findKeyBoardFocus()
            registerCarrierEditText()
        }


    @get:Bindable
    override var mobileNoLength: Int = 11
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileNoLength)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var errorMessage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMessage)
            if (errorMessage.isNotEmpty()) {
                background = context.getDrawable(R.drawable.bg_red_line)
                drawbleRight =
                    context!!.resources.getDrawable(co.yap.yapcore.R.drawable.ic_error, null)
            } else {
                background = context.getDrawable(R.drawable.bg_plain_edit_text)
                drawbleRight = null
            }
        }


    @get:Bindable
    override var errorMessageConfirmMobile: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMessageConfirmMobile)
            if (errorMessageConfirmMobile.isNotEmpty()) {
                backgroundConfirmMobile = context.getDrawable(R.drawable.bg_red_line)
                drawbleRight =
                    context!!.resources.getDrawable(co.yap.yapcore.R.drawable.ic_error, null)
            } else {
                backgroundConfirmMobile = context.getDrawable(R.drawable.bg_plain_edit_text)
                drawbleRight = null
            }
        }


    fun registerCarrierEditText() {

        val ccpLoadNumber: CountryCodePicker? = CountryCodePicker(context)
        ccpLoadNumber!!.registerCarrierNumberEditText(this.etMobileNumber!!)

        ccpLoadNumber.setPhoneNumberValidityChangeListener(object :
            CountryCodePicker.PhoneNumberValidityChangeListener {
            override fun onValidityChanged(isValidNumber: Boolean) {
                if (isValidNumber) {
                    mobileNoLength = 11
                    if (mobile.length == 11) {
                        setSuccessUI()
                        drawbleRight =
                            context!!.resources.getDrawable(co.yap.yapcore.R.drawable.path, null)
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

    fun findKeyBoardFocus() {
        etMobileNumber!!.getViewTreeObserver().addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    /* if (etMobileNumber!!.isFocused()) {
                         if (!keyboardShown(etMobileNumber!!.getRootView())) {
 //                            activeFieldValue = false
                         } else {
 //                            activeFieldValue = true
                         }
                     }*/
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

    fun setSuccessUI() {
        drawbleRight = null
        background =
            context!!.resources.getDrawable(R.drawable.bg_plain_edit_text, null)
//        activeFieldValue = true
//        mobileError = ""
        valid = false

    }

    fun setErrorLayout() {
        drawbleRight = context.getDrawable(R.drawable.ic_error)
        background = context!!.resources.getDrawable(R.drawable.bg_red_line, null)
    }

}