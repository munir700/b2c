package co.yap.yapcore.helpers.validation.rule

import android.util.Patterns
import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.EditTextHandler.removeError

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
class EmailTypeRule(
    view: TextView?,
    errorMessage: String?,
    errorEnabled: Boolean
) : TypeRule(
    view,
    FieldType.Email,
    errorMessage,
    errorEnabled
) {
    protected override fun isValid(view: TextView?): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(view?.text).matches()
    }

    protected override fun onValidationSucceeded(view: TextView?) {
        super.onValidationSucceeded(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)
                    ?.apply { setEndIconDrawable(R.drawable.path) }
            }
            removeError(view)
        }
    }

    protected override fun onValidationFailed(view: TextView?) {
        super.onValidationFailed(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)?.apply {
                    endIconDrawable = null
                }
            }
        }
       // if (errorEnabled) setError(view, errorMessage)
    }
}