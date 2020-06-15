package co.yap.yapcore.helpers.validation.rule

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import co.yap.yapcore.helpers.extentions.isEmpty
import co.yap.yapcore.helpers.validation.util.EditTextHandler

/**
 * Created irfan arshad on 10/6/2020.
 */
class EmptyRule(
    view: TextView?,
    value: Boolean?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<TextView?, Boolean?>(
    view,
    value,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: TextView?): Boolean {
        return if (view?.visibility == View.GONE) true else !value!! || !isEmpty(view?.text)
    }

    override fun onValidationSucceeded(view: TextView?) {
        EditTextHandler.removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        if (errorEnabled) EditTextHandler.setError(view, errorMessage)
    }
}
