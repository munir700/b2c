package co.yap.yapcore.helpers.validation.binding

import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.rule.TypeRule
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper
import co.yap.yapcore.helpers.validation.util.ViewTagHelper

/**
 * Created irfan arshad on 10/6/2020.
 */

object TypeBindings {
    @JvmStatic
    @BindingAdapter(
        value = ["validateType", "validateTypeMessage", "validateTypeAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    fun bindingTypeValidation(
        view: TextView?,
        fieldTypeText: String,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        try {

            val fieldType =
                getFieldTypeByText(fieldTypeText)

            val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
                view,
                errorMessage, fieldType.errorMessageId
            )
            ViewTagHelper.appendValue(
                R.id.validator_rule,
                view,
                fieldType.instantiate(view, handledErrorMessage, errorEnabled)
            )
        } catch (ignored: Exception) {
            Toast.makeText(view?.context, ignored.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun getFieldTypeByText(fieldTypeText: String): TypeRule.FieldType {
        var fieldType =
            TypeRule.FieldType.None
        for (type in TypeRule.FieldType.values()) {
            if (type.toString().equals(fieldTypeText, ignoreCase = true)) {
                fieldType = type
                break
            }
        }
        return fieldType
    }
}