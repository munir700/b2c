package co.yap.yapcore.helpers.validation.binding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import co.yap.yapcore.R;
import co.yap.yapcore.helpers.validation.rule.ConfirmPasswordRule;
import co.yap.yapcore.helpers.validation.util.EditTextHandler;
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper;
import co.yap.yapcore.helpers.validation.util.ViewTagHelper;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class PasswordBindings {

    @BindingAdapter(value = {"validatePassword", "validatePasswordMessage", "validatePasswordAutoDismiss"}, requireAll = false)
    public static void bindingPassword(TextView view, TextView comparableView, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.error_message_not_equal_password);
        ViewTagHelper.appendValue(R.id.validator_rule, view,
                new ConfirmPasswordRule(view, comparableView, handledErrorMessage));
    }

}
