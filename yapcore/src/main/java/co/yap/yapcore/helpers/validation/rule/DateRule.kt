package co.yap.yapcore.helpers.validation.rule

import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.helpers.validation.util.DateValidator
import co.yap.yapcore.helpers.validation.util.EditTextHandler.removeError

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
class DateRule(
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
    private val dateValidator: DateValidator = DateValidator()
    override fun isValid(view: TextView?): Boolean {
        return dateValidator.isValid(view?.text.toString(), value)
    }

    override fun onValidationSucceeded(view: TextView?) {
        removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
//        setError(view, errorMessage)
    }

}