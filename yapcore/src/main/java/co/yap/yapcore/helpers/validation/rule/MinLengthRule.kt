package co.yap.yapcore.helpers.validation.rule

import android.view.View
import android.widget.TextView
import co.yap.yapcore.helpers.validation.util.EditTextHandler

/**
 * Created irfan arshad on 10/6/2020.
 */
class MinLengthRule(
    view: TextView?,
    value: Int?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<TextView?, Int?>(
    view,
    value,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: TextView?): Boolean {
        return view?.visibility == View.GONE || view?.length()!! >= value!!
    }

    override fun onValidationSucceeded(view: TextView?) {
        EditTextHandler.removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        if (errorEnabled) EditTextHandler.setError(view, errorMessage)
    }
}