package co.yap.yapcore.helpers.validation.rule

import android.view.View
import androidx.annotation.Keep
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.parseToInt
import co.yap.yapcore.helpers.getCountryCodeForRegion
import co.yap.yapcore.helpers.isValidPhoneNumber
import com.google.android.material.textfield.TextInputEditText
import co.yap.yapcore.helpers.validation.util.EditTextHandler

@Keep
class MobileNoRule(
    view: TextInputEditText?,
    value: String?,
    errorMessage: String?,
    errorEnabled: Boolean, val isOptional: Boolean = false
) : Rule<TextInputEditText?, String?>(
    view,
    value,
    errorMessage,
    errorEnabled
) {
    override fun isValid(view: TextInputEditText?): Boolean {
        if (isOptional && !view?.text.isNullOrBlank()) {
            return isValidPhoneNumber(
                view?.text.toString(),
                getCountryCodeForRegion(value?.parseToInt()!!)
            )
        }
        if (isOptional) {
            return true
        }

        return view?.visibility == View.GONE || isOptional || isValidPhoneNumber(
            view?.text.toString(),
            getCountryCodeForRegion(value?.parseToInt()!!)
        )
    }

    override fun onValidationSucceeded(view: TextInputEditText?) {
        super.onValidationSucceeded(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)
                    ?.apply { setEndIconDrawable(R.drawable.path) }
//                setCompoundDrawablesWithIntrinsicBounds(
//                    compoundDrawables[0],
//                    compoundDrawables[1],
//                    getDrawable(R.drawable.path),
//                    compoundDrawables[3]
//                )
            }
//            EditTextHandler.removeError(view)
        }
    }

    override fun onValidationFailed(view: TextInputEditText?) {
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)?.apply {
                    endIconDrawable = null
                }
            }
        }
//        if (errorEnabled) {
//            view?.apply {
//                setCompoundDrawablesWithIntrinsicBounds(
//                    compoundDrawables[0],
//                    compoundDrawables[1],
//                    null,
//                    compoundDrawables[3]
//                )
//            }
////            EditTextHandler.setError(view, errorMessage)
//        }
    }
}
