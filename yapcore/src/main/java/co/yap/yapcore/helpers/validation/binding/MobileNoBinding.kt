package co.yap.yapcore.helpers.validation.binding

import android.widget.TextView
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter
import co.yap.widgets.PrefixSuffixEditText
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.rule.ConfirmMobileNoRule
import co.yap.yapcore.helpers.validation.rule.MobileNoRule
import co.yap.yapcore.helpers.validation.util.EditTextHandler
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper
import co.yap.yapcore.helpers.validation.util.ViewTagHelper

@Keep
object MobileNoBinding {
    @JvmStatic
    @BindingAdapter(
        value = ["isoCountryCode", "validateMobileMessage", "validateMobileAutoDismiss", "errorEnabled", "isOptional"],
        requireAll = false
    )
    fun bindPhone(
        view: PrefixSuffixEditText?,
        countryCode: String?,
        errorMessage: String?,
        autoDismiss: Boolean, errorEnabled: Boolean, isOptional: Boolean = false
    ) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view)
        }
        countryCode?.let { view?.prefix = it }
        val handledErrorMessage: String = ErrorMessageHelper.getStringOrDefault(
            view,
            errorMessage, R.string.error_message_first_name_validation
        )
        ViewTagHelper.appendValue(
            R.id.validator_rule,
            view,
            MobileNoRule(view, countryCode?.replace("+", ""), handledErrorMessage, errorEnabled,isOptional)
        )
    }

    @BindingAdapter(
        value = ["validateMobile", "validateMobileMessage", "validateMobileAutoDismiss"],
        requireAll = false
    )
    @JvmStatic
    fun bindingPassword(
        view: TextView?,
        comparableView: TextView?,
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