package co.yap.yapcore.helpers.validation.rule

import android.view.View
import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.util.EditTextHandler

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
class FirstNameRule(view: TextView, errorMessage: String, errorEnabled: Boolean) :
    TypeRule(view, FieldType.FirstName, errorMessage, errorEnabled) {
    override fun isValid(view: TextView?): Boolean {
        view?.let {
            val username = it.text.toString()
            val expression =
                "^[a-zA-Z]{1}[a-zA-Z ]{1,100}\$"
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
    }
}
