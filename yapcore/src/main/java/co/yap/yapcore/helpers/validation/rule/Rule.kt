package co.yap.yapcore.helpers.validation.rule

import android.view.View
import androidx.annotation.Keep

/**
 * Created irfan arshad on 10/6/2020.
 */
@Keep
abstract class Rule<ViewType : View?, ValueType>(
    var view: ViewType,
    var value: ValueType,
    protected var errorMessage: String?,
    protected var errorEnabled: Boolean
) {

    fun validate(): Boolean {
        val valid = isValid(view)
        if (valid) {
            onValidationSucceeded(view)
        } else {
            onValidationFailed(view)
        }
        return valid
    }

    protected abstract fun isValid(view: ViewType): Boolean
    protected open fun onValidationSucceeded(view: ViewType) {}
    protected open fun onValidationFailed(view: ViewType) {}

}