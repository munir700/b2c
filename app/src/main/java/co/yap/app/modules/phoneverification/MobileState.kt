package co.yap.app.modules.phoneverification

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.modules.onboarding.models.CountryCode
import co.yap.widgets.mobile.CountryCodePicker
import co.yap.yapcore.BaseState
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.leanplum.SignupEvents
import co.yap.yapcore.leanplum.trackEvent

class MobileState(application: Application, var viewModel: MobileViewModel) : BaseState(),
    IMobile.State {

    val VISIBLE: Int = 0x00000000
    val GONE: Int = 0x00000008
    val mContext = application.applicationContext


    @get:Bindable
    override var background: Drawable? =
        mContext.getDrawable(R.drawable.bg_round_edit_text)
        set(value) {
//            setDrawabeTint()
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
    override var mobile: String = ""
        set(value) {
            field = value
            if (viewModel.isPhoneNumberEntered.value == false) {
                viewModel.isPhoneNumberEntered.value = true
                trackEvent(SignupEvents.SIGN_UP_START.type)
            }
            if (mobile.length < 9) {
                mobileNoLength = 11
            }
            notifyPropertyChanged(BR.mobile)
        }

    @get:Bindable
    override var mobileError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileError)
            setErrorResponseLayout()

        }

    override var valid: ObservableBoolean = ObservableBoolean()

    @get:Bindable
    override var activeFieldValue: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.activeFieldValue)
        }

//    @get:Bindable
//    override var errorVisibility: Int = VISIBLE
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.handleBackPress)
//        }

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
        ccpLoadNumber?.registerCarrierNumberEditText(this.etMobileNumber!!)

        ccpLoadNumber?.setPhoneNumberValidityChangeListener(object :
            CountryCodePicker.PhoneNumberValidityChangeListener {
            override fun onValidityChanged(isValidNumber: Boolean) {
                if (isValidNumber) {
                    mobileNoLength = 11
                    if (mobile.length == 11) {
                        setSuccessUI()
                        setDrawableTint()
                        valid.set(true)

                    } else {
                        setSuccessUI()
                    }
                } else {
                    setSuccessUI()
                    if (mobile.replace(" ", "").trim().length >= 9) {
                        setErrorLayout()
                    } else {
                        setSuccessUI()
                    }
                }
            }
        })
    }

    private fun findKeyBoardFocus() {
        etMobileNumber?.let {
            it.viewTreeObserver?.addOnGlobalLayoutListener(
                ViewTreeObserver.OnGlobalLayoutListener {
                    if (it.isFocused) {
                        activeFieldValue = keyboardShown(it.rootView)
                    }
                    return@OnGlobalLayoutListener
                })
        }
    }

    private fun keyboardShown(rootView: View): Boolean {
        val softKeyboardHeight = 100
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff = rootView.bottom - r.bottom
        return heightDiff > softKeyboardHeight * dm.density
    }

    private fun setErrorLayout() {
        mobileNoLength = 9
        valid.set(false)

    }

    private fun setErrorResponseLayout() {
        if (!mobileError.isNullOrEmpty()) {

            drawbleRight = mContext.resources.getDrawable(R.drawable.invalid_name)
            background =
                mContext.resources.getDrawable(R.drawable.bg_round_error_layout)
            //valid = false
        }
    }

    private fun setSuccessUI() {
        drawbleRight = null
        background = mContext.resources.getDrawable(R.drawable.bg_round_edit_text)
        activeFieldValue = true
        mobileError = ""
        valid.set(false)

    }

    @SuppressLint("ResourceType")
    fun setDrawableTint() {
        drawbleRight = mContext.getDrawable(R.drawable.path)?.let { DrawableCompat.wrap(it) }
        drawbleRight?.let {
            if (SharedPreferenceManager.getInstance(mContext).getThemeValue()
                    .equals(Constants.THEME_HOUSEHOLD)
            ) {
                DrawableCompat.setTint(it, Color.RED)
            }
        }

    }

    override var countryCode: MutableLiveData<String> =
        MutableLiveData(CountryCode.UAE.countryCode ?: "")
    override var mobileNumber: MutableLiveData<String> = MutableLiveData("")

    override var totalProgress: ObservableField<Int> = ObservableField(100)

    override var currentProgress: ObservableField<Int> = ObservableField(0)
}