package co.yap.yapcore.helpers.validation.binding

import androidx.databinding.BindingAdapter
import co.yap.widgets.EasyMoneyEditText
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.validation.rule.CurrencyInputRule
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper
import co.yap.yapcore.helpers.validation.util.ViewTagHelper

object CurrencyInputBinding {
    @BindingAdapter(
        value = ["validAmount", "validateAmountMessage", "validateAmountAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingCurrency(
        view: EasyMoneyEditText?,
        validAmount: String?,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_not_equal_password
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule, view,
            CurrencyInputRule(view, validAmount.parseToDouble(), handledErrorMessage, errorEnabled)
        )
    }
}