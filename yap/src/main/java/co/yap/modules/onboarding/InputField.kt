package co.yap.modules.onboarding

import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.yapcore.interfaces.IBindable


class InputField : BaseObservable(), IBindable {
    override var bindingVariable: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    private var text: String? = null
    private var error: String? = null
    @get:Bindable
    var defaultError: String? = null
        set(defaultError) {
            field = defaultError
            notifyPropertyChanged(BR.defaultError)
        }
    @get:Bindable
    var isEnabled = true
        set(enabled) {
            field = enabled
            notifyPropertyChanged(BR.enabled)
        }
    @get:Bindable
    var imeOptions = EditorInfo.IME_ACTION_NEXT
        set(imeOptions) {
            field = imeOptions
            notifyPropertyChanged(BR.imeOptions)
        }
    @get:Bindable
    var inputType = EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE
        set(inputType) {
            field = inputType
            notifyPropertyChanged(BR.inputType)
        }
//    @get:Bindable
//    var validationErrorIcon = R.drawable.invalid_name
//        set(validationErrorIcon) {
//            field = validationErrorIcon
//            notifyPropertyChanged(BR.validationErrorIcon)
//            notifyPropertyChanged(BR.validationIcon)
//        }
//    @get:Bindable
//    var validationSuccessIcon = R.drawable.invalid_name
//        set(validationSuccessIcon) {
//            field = validationSuccessIcon
//            notifyPropertyChanged(BR.validationSuccessIcon)
//            notifyPropertyChanged(BR.validationIcon)
//        }
    var onFocusChangeListener: View.OnFocusChangeListener? = null
    @get:Bindable
    var isOptional = false
        set(optional) {
            field = optional
            notifyPropertyChanged(BR.optional)
        }
    private val allCaps = false

//    val bindingVariable: Int
//        get() = BR.input
//
//    val isValid: Boolean
//        @Bindable
//        get() = !TextUtils.isEmpty(getText()) && TextUtils.isEmpty(getError())
//
//    val validationIcon: Int
//        @Bindable
//        get() =
//            if (TextUtils.isEmpty(getError()) && TextUtils.isEmpty(getText())) -1 else if (isValid) validationSuccessIcon else validationErrorIcon
//
//    var focusChangeListener = { view, isFocused ->
//        if (isFocused) {
//            // clear errors if any
//            setError(null)
//        } else {
//            // validate
//            if (!isValid && !isOptional) {
//                setError(defaultError)
//            }
//        }
//
//        // pass the event to user's requested listener
//        if (onFocusChangeListener != null) onFocusChangeListener!!.onFocusChange(view, isFocused)
//    }

//    fun clear() {
//        setText("")
//        setError(null)
//    }

    @Bindable
    fun getText(): String {
        if (text == null) text = ""
        return text as String
    }
//
//    fun setText(inputText: String) {
//        this.text = inputText
//        notifyPropertyChanged(BR.text)
//        notifyPropertyChanged(BR.valid)
//        notifyPropertyChanged(BR.validationIcon)
//    }
//
//    @Bindable
//    fun getError(): String {
//        if (error == null) error = ""
//        return error as String
//    }
//
//    fun setError(error: String?) {
//        this.error = error
//        notifyPropertyChanged(BR.error)
//        notifyPropertyChanged(BR.valid)
//        notifyPropertyChanged(BR.validationIcon)
//    }

}
