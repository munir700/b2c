package co.yap.yapcore.helpers.validation.rule

import android.widget.TextView
import co.yap.yapcore.helpers.validation.util.EditTextHandler.removeError
import co.yap.yapcore.helpers.validation.util.EditTextHandler.setError

/**
 * Created irfan arshad on 10/6/2020.
 */
class ConfirmPasswordRule(
    view: TextView?,
    value: TextView?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<TextView?, TextView?>(
    view,
    value,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: TextView?): Boolean {
        if (value == null) return false
        val value1 = view?.text.toString()
        val value2 = value?.text.toString()
        return value1.trim { it <= ' ' } == value2.trim { it <= ' ' }
    }

    override fun onValidationSucceeded(view: TextView?) {
        removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        setError(view, errorMessage)
    }
}