package co.yap.yapcore.helpers.validation.rule

import androidx.core.content.ContextCompat
import co.yap.widgets.EasyMoneyEditText
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.isEmpty
import co.yap.yapcore.helpers.extentions.parseToDouble

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
        view?.apply {
            if (errorEnabled) background = ContextCompat.getDrawable(context, R.drawable.bg_funds)
        }
    }

    override fun onValidationFailed(view: EasyMoneyEditText?) {
        view?.apply {
            if (errorEnabled && valueString.parseToDouble() > (value ?: 0.00)) background =
                ContextCompat.getDrawable(context, R.drawable.bg_funds_error)
        }
    }
}
