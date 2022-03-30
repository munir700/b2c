package co.yap.yapcore.helpers.validation.rule

import android.view.View
import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.util.EditTextHandler
@Keep
class LastNameRule(view: TextView, errorMessage: String, errorEnabled: Boolean) :
    TypeRule(view, FieldType.LastName, errorMessage, errorEnabled) {
    override fun isValid(view: TextView?): Boolean {
        view?.let {
            val username = it.text.toString()
            val expression =
                "^[a-zA-Z]{1,100}\$"
            return it.visibility == View.GONE || username.matches(expression.toRegex())
        }
        return false
    }

    override fun onValidationSucceeded(view: TextView?) {
        super.onValidationSucceeded(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)
                    ?.apply { setEndIconDrawable(R.drawable.path) }
            }
            //view?.let { removeError(it) }
        }
//        view?.let { EditTextHandler.removeError(it) }
    }

    override fun onValidationFailed(view: TextView?) {
        super.onValidationFailed(view)
        if (errorEnabled) {
            view?.apply {
                EditTextHandler.getTextInputLayout(view)?.apply {
                    endIconDrawable = null
                }
            }
        }
//        view?.let { if (errorEnabled) EditTextHandler.setError(it, errorMessage) }

    }
}