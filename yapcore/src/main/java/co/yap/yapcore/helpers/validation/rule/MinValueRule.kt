package co.yap.yapcore.helpers.validation.rule

import android.view.View
import android.widget.TextView
import androidx.annotation.Keep
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.validation.util.EditTextHandler

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
class MinValueRule(
    view: TextView?,
    minimumvalue: String?,
    errorMessage: String?,
    showErrorMessage: Boolean
) : Rule<TextView?, String?>(
    view,
    minimumvalue,
    errorMessage,
    showErrorMessage
) {
    override fun isValid(view: TextView?): Boolean {
        return view?.visibility == View.GONE || view?.text.toString().replace(",","").parseToDouble() >= value?.parseToDouble() ?: 0.0
    }

    override fun onValidationSucceeded(view: TextView?) {
        if (errorEnabled)  EditTextHandler.removeError(view)
    }

    override fun onValidationFailed(view: TextView?) {
        if (errorEnabled) EditTextHandler.setError(view, errorMessage)
    }
}