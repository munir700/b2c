package co.yap.yapcore.helpers.validation.binding

import androidx.databinding.BindingAdapter
import co.yap.yapcore.R
import com.google.android.material.textfield.TextInputEditText
import co.yap.yapcore.helpers.validation.rule.ConfirmMobileNoRule
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper
import co.yap.yapcore.helpers.validation.util.ViewTagHelper

/**
 * Created irfan arshad on 10/6/2020.
 */
object PasswordBindings {
    @BindingAdapter(
        value = ["validatePassword", "validatePasswordMessage", "validatePasswordAutoDismiss", "errorEnabled"],
        requireAll = false
    )
    @JvmStatic
    fun bindingPassword(
        view: TextInputEditText?,
        comparableView: TextInputEditText?,
        errorMessage: String?,
        autoDismiss: Boolean,
        errorEnabled: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_not_equal_password
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule, view,
            ConfirmMobileNoRule(view, comparableView, handledErrorMessage, errorEnabled)
        )
    }

    @BindingAdapter(
        value = ["validateIban", "validateIbanMessage", "validateIbanAutoDismiss"],
        requireAll = false
    )
    @JvmStatic
    fun bindingIban(
        view: TextInputEditText?,
        comparableView: TextInputEditText?,
        errorMessage: String?,
        autoDismiss: Boolean
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        val handledErrorMessage = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_not_equal_phone
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule, view,
            ConfirmMobileNoRule(view, comparableView, handledErrorMessage, false)
        )
    }
}