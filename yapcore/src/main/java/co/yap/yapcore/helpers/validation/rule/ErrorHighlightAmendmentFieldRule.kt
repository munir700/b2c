package co.yap.yapcore.helpers.validation.rule

import android.view.View
import androidx.annotation.Keep
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import com.google.android.material.textfield.TextInputEditText

@Keep
class ErrorHighlightAmendmentFieldRule(
    view: TextInputEditText?,
    value: Boolean,
    val isNotNeedToCheckWithPrevious: Boolean = false,
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
    var tagFound: Boolean = false
    override fun isValid(view: TextInputEditText?): Boolean {
        if (view?.visibility == View.GONE || view?.visibility == View.INVISIBLE) {
            return true
        }
        missingFieldMap?.let { it ->
            it.values.toList().forEach { it ->
                it?.forEach {
                    if (view?.tag == it && isNotNeedToCheckWithPrevious) {
                        tagFound = true
                        return tagFound
                    } else if (view?.tag == it && previousValue != null && view.text.toString() == previousValue
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
        if (tagFound && view?.text.toString() == previousValue) {
            showError(view)
        } else {
            EditTextHandler.getTextInputLayout(view)?.apply {
                error = ""
            }
            if (errorEnabled) {
                view?.apply {
                    view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
        }
    }

    override fun onValidationFailed(view: TextInputEditText?) {
        showError(view)
    }

    private fun showError(view: TextInputEditText?) {
        EditTextHandler.getTextInputLayout(view)?.apply {
            error = if (errorMessage.isNullOrBlank()) Translator.getString(
                context,
                Strings.kyc_incorrect_field
            ) else errorMessage
        }
        if (errorEnabled) {
            view?.apply {
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            }
        }
    }
}
