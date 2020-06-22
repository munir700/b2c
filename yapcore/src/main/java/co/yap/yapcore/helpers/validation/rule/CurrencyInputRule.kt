package co.yap.yapcore.helpers.validation.rule

import android.view.View
import co.yap.widgets.EasyMoneyEditText
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.isEmpty
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.spannables.underline
import co.yap.yapcore.helpers.validation.util.EditTextHandler

class CurrencyInputRule(
    view: EasyMoneyEditText?,
    value: Double?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<EasyMoneyEditText?, Double?>(
    view,
    value,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: EasyMoneyEditText?): Boolean {
        if (isEmpty(view))
            return false
        else if (view?.valueString.parseToDouble() > (value ?: 0.00)) {
            return false
        }
        return true
    }

    override fun onValidationSucceeded(view: EasyMoneyEditText?) {
        EditTextHandler.removeError(view)
    }

    override fun onValidationFailed(view: EasyMoneyEditText?) {
        if (errorEnabled) EditTextHandler.setError(view, errorMessage)
    }
}
