package co.yap.yapcore.helpers.validation.rule

import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.helpers.validation.util.EditTextHandler

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
class RegexRule(
    view: TextView?,
    value: String?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<TextView?, String?>(
    view,
    value,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: TextView?): Boolean {
        return view?.text.toString().matches(value?.toRegex()!!)
    }

    override fun onValidationSucceeded(view: TextView?) {
        EditTextHandler.removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        if (errorEnabled) EditTextHandler.setError(view, errorMessage)
    }
}