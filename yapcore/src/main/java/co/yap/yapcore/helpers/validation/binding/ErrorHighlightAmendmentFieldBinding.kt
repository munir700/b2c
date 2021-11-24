package co.yap.yapcore.helpers.validation.binding

import androidx.databinding.BindingAdapter
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.rule.ErrorHighlightAmendmentFieldRule
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper
import co.yap.yapcore.helpers.validation.util.ViewTagHelper
import com.google.android.material.textfield.TextInputEditText

object ErrorHighlightAmendmentFieldBinding {
    @JvmStatic
    @BindingAdapter(
        value = ["highlightAmendmentField", "validateHighlightAmendmentFieldMessage", "validateHighlightAmendmentFieldAutoDismiss", "enableError", "previousValue", "missingFieldMap"],
        requireAll = false
    )
    fun bindErrorHighlighter(
        view: TextInputEditText?,
        value: Boolean,
        errorMessage: String?,
        autoDismiss: Boolean,
        enableError: Boolean,
        previousValue: String? = null,
        missingFieldMap: HashMap<String?, List<String>?>? = null
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage: String = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_first_name_validation
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            ErrorHighlightAmendmentFieldRule(
                view,
                value,
                handledErrorMessage,
                enableError,
                previousValue,
                missingFieldMap
            )
        )
    }
}