package co.yap.yapcore.helpers.validation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.rule.RegexRule
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper
import co.yap.yapcore.helpers.validation.util.ViewTagHelper

/**
 * Created irfan arshad on 10/6/2020.
 */
object RegexBindings {
    @BindingAdapter(
        value = ["validateRegex", "validateRegexMessage", "validateRegexAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingRegex(
        view: TextView?,
        pattern: String?,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_regex_validation
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            RegexRule(view, pattern, handledErrorMessage, errorEnabled)
        )
    }
}