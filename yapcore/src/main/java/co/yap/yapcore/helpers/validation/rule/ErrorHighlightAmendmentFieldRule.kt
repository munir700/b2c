package co.yap.yapcore.helpers.validation.rule

import androidx.annotation.Keep
import co.yap.yapcore.R
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
                    if (view?.tag == it && previousValue != null && view.text.toString().trim() == previousValue) {
                        return false
                    }
                }
            }
        }
        return true
    }

    override fun onValidationSucceeded(view: TextInputEditText?) {
        super.onValidationSucceeded(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)
                    ?.apply {
                        setEndIconDrawable(R.drawable.path)
                    }
            }
        }
    }

    override fun onValidationFailed(view: TextInputEditText?) {
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)?.apply {
                    endIconDrawable = null
                }
            }
        } else {
            EditTextHandler.getTextInputLayout(view)?.apply {
                error = "Show Error"
            }
        }
    }
}
