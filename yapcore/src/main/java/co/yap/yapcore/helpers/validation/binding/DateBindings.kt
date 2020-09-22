package co.yap.yapcore.helpers.validation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.rule.DateRule
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper
import co.yap.yapcore.helpers.validation.util.ViewTagHelper

/**
 * Created irfan arshad on 10/6/2020.
 */
object DateBindings {
    @BindingAdapter(
        value = ["validateDate", "validateDateMessage", "validateDateAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingDate(
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
            errorMessage, R.string.error_message_date_validation
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            DateRule(view, pattern, handledErrorMessage, errorEnabled)
        )
    }
}