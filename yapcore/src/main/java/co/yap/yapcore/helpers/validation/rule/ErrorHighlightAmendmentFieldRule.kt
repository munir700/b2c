package co.yap.yapcore.helpers.validation.rule

import androidx.annotation.Keep
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import com.google.android.material.textfield.TextInputEditText

@Keep
class ErrorHighlightAmendmentFieldRule(
    view: TextInputEditText?,
    value: Boolean,
    errorMessage: String?,
    errorEnabled: Boolean,
    val previousValue: String? = null,
    val missingFieldMap: HashMap<String?, List<String>?>? = null
) : Rule<TextInputEditText?, Boolean>(
    view,
    value,
    errorMessage,
    errorEnabled
) {
    override fun isValid(view: TextInputEditText?): Boolean {
        missingFieldMap?.let { it ->
            it.values.toList().forEach { it ->
                it?.forEach {
                    if (view?.tag == it && previousValue != null && view.text.toString()
                            .trim().isNotBlank() && view.text.toString()
                            .trim().replace("-", "") == previousValue
                    ) {
                        return false
                    }
                }
            }
        }
        return true
    }

    override fun onValidationSucceeded(view: TextInputEditText?) {
        super.onValidationSucceeded(view)
        EditTextHandler.getTextInputLayout(view)?.apply {
            error = ""
        }
    }

    override fun onValidationFailed(view: TextInputEditText?) {
        EditTextHandler.getTextInputLayout(view)?.apply {
            error = Translator.getString(context, Strings.kyc_incorrect_field)
        }
    }
}
