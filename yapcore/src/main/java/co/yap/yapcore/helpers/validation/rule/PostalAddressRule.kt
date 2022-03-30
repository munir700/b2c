package co.yap.yapcore.helpers.validation.rule

import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.helpers.validation.util.EditTextHandler.removeError
import co.yap.yapcore.helpers.validation.util.EditTextHandler.setError

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
class PostalAddressRule(
    view: TextView?,
    value: FieldType?,
    errorMessage: String?,
    errorEnabled: Boolean
) : TypeRule(view, value, errorMessage, errorEnabled) {
    protected override fun isValid(view: TextView?): Boolean {
        val postalAddress = view?.text.toString()
        return postalAddress.matches("\\\\d+\\\\s+([a-zA-Z]+|[a-zA-Z]+\\\\s[a-zA-Z]+)".toRegex())
    }

    protected override fun onValidationSucceeded(view: TextView?) {
        super.onValidationSucceeded(view)
        removeError(view)
    }

    protected override fun onValidationFailed(view: TextView?) {
        super.onValidationFailed(view)
        if (errorEnabled) setError(view, errorMessage)
    }
}
