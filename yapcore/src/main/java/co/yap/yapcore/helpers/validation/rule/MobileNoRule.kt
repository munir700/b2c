package co.yap.yapcore.helpers.validation.rule

import android.text.TextUtils
import android.view.View
import co.yap.widgets.PrefixSuffixEditText
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.getDrawable
import co.yap.yapcore.helpers.extentions.parseToInt
import co.yap.yapcore.helpers.getCountryCodeForRegion
import co.yap.yapcore.helpers.isValidPhoneNumber
import co.yap.yapcore.helpers.validation.util.EditTextHandler

class MobileNoRule(
    view: PrefixSuffixEditText?,
    value: String?,
    errorMessage: String?,
    errorEnabled: Boolean,val isOptional: Boolean = false
) : Rule<PrefixSuffixEditText?, String?>(
    view,
    value,
    errorMessage,
    errorEnabled
) {
    override fun isValid(view: PrefixSuffixEditText?): Boolean {
        if(isOptional && !view?.text.isNullOrBlank())
        {
            return isValidPhoneNumber(
                view?.text.toString(),
                getCountryCodeForRegion(value?.parseToInt()!!)
            )
        }
        if(isOptional )
        {
            return true
        }

        return view?.visibility == View.GONE || isOptional || isValidPhoneNumber(
            view?.text.toString(),
            getCountryCodeForRegion(value?.parseToInt()!!)
        )
    }

    override fun onValidationSucceeded(view: PrefixSuffixEditText?) {
        super.onValidationSucceeded(view)
        if (errorEnabled) {
            view?.apply {
                setCompoundDrawablesWithIntrinsicBounds(
                    compoundDrawables[0],
                    compoundDrawables[1],
                    getDrawable(R.drawable.path),
                    compoundDrawables[3]
                )
            }
//            EditTextHandler.removeError(view)
        }
    }

    override fun onValidationFailed(view: PrefixSuffixEditText?) {
        if (errorEnabled) {
            view?.apply {
                setCompoundDrawablesWithIntrinsicBounds(
                    compoundDrawables[0],
                    compoundDrawables[1],
                    null,
                    compoundDrawables[3]
                )
            }
//            EditTextHandler.setError(view, errorMessage)
        }
    }
}
