package co.yap.yapcore.helpers.validation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.rule.EmptyRule
import co.yap.yapcore.helpers.validation.rule.MaxLengthRule
import co.yap.yapcore.helpers.validation.rule.MinLengthRule
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper
import co.yap.yapcore.helpers.validation.util.ViewTagHelper

/**
 * Created irfan arshad on 10/6/2020.
 */
object LengthBindings {
    @BindingAdapter(
        value = ["validateMinLength", "validateMinLengthMessage", "validateMinLengthAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingMinLength(
        view: TextView?,
        minLength: Int,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.default_required_length_message_min, minLength
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            MinLengthRule(view, minLength, handledErrorMessage, errorEnabled)
        )
    }

    @BindingAdapter(
        value = ["validateMaxLength", "validateMaxLengthMessage", "validateMaxLengthAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingMaxLength(
        view: TextView?,
        maxLength: Int,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.default_required_length_message_max, maxLength
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            MaxLengthRule(view, maxLength, handledErrorMessage, errorEnabled)
        )
    }

    @BindingAdapter(
        value = ["validateEmpty", "validateEmptyMessage", "validateEmptyAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingEmpty(
        view: TextView?,
        empty: Boolean,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_empty_validation
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            EmptyRule(view, empty, handledErrorMessage, errorEnabled)
        )
    }
}