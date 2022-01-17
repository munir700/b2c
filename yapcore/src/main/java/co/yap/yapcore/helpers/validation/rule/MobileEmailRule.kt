package co.yap.yapcore.helpers.validation.rule

import android.util.Patterns
import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.R
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.isValidPhoneNumber
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.EditTextHandler.removeError
import co.yap.yapcore.helpers.validation.util.EditTextHandler.setError

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
class MobileEmailRule(view: TextView, errorMessage: String, errorEnabled: Boolean) :
    TypeRule(view, FieldType.MobileEmail, errorMessage, errorEnabled) {
    override fun isValid(view: TextView?): Boolean {
        return (Utils.isUsernameNumeric(view?.text.toString()) && isValidPhoneNumber(
            view?.text.toString(),
            "AE"
        )) || Patterns.EMAIL_ADDRESS.matcher(view?.text.toString()).matches()

    }

    override fun onValidationSucceeded(view: TextView?) {
        super.onValidationSucceeded(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)
                    ?.apply { setEndIconDrawable(R.drawable.path) }
            }
            view?.let { removeError(it) }
        }

    }

    override fun onValidationFailed(view: TextView?) {
        super.onValidationFailed(view)
        view?.let {
            if (errorEnabled)
                setError(it, errorMessage)
        }
    }
}
