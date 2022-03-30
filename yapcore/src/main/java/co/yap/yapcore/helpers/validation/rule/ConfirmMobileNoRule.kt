package co.yap.yapcore.helpers.validation.rule

import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.getDrawable
import co.yap.yapcore.helpers.extentions.isEmpty
import com.google.android.material.textfield.TextInputEditText
import co.yap.yapcore.helpers.validation.util.EditTextHandler.removeError

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
class ConfirmMobileNoRule(
    view: TextView?,
    value: TextView?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<TextView?, TextView?>(
    view,
    value,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: TextView?): Boolean {
        if (value == null) return false
        val value1 = view?.text.toString()
        val value2 = value?.text.toString()
        return !isEmpty(value1) && value1.trim { it <= ' ' } == value2.trim { it <= ' ' }
    }

    override fun onValidationSucceeded(view: TextView?) {
        view?.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                compoundDrawables[0],
                compoundDrawables[1],
                getDrawable(R.drawable.path),
                compoundDrawables[3]
            )
        }
        removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        view?.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                compoundDrawables[0],
                compoundDrawables[1],
                null,
                compoundDrawables[3]
            )
        }
    }
}