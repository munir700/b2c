package co.yap.yapcore.helpers.validation.rule

import android.util.Patterns
import android.widget.TextView
import co.yap.yapcore.helpers.validation.util.EditTextHandler.removeError
import co.yap.yapcore.helpers.validation.util.EditTextHandler.setError

/**
 * Created irfan arshad on 10/6/2020.
 */

class EmailTypeRule(
    view: TextView?,
    errorMessage: String?,
    errorEnabled: Boolean
) : TypeRule(
    view,
    FieldType.Email,
    errorMessage,
    errorEnabled
) {
    protected override fun isValid(view: TextView?): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(view?.text).matches()
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