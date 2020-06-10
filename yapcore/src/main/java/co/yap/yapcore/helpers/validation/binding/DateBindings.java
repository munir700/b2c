package co.yap.yapcore.helpers.validation.binding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import co.yap.yapcore.R;
import co.yap.yapcore.helpers.validation.rule.DateRule;
import co.yap.yapcore.helpers.validation.util.EditTextHandler;
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper;
import co.yap.yapcore.helpers.validation.util.ViewTagHelper;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class DateBindings {

    @BindingAdapter(value = {"validateDate", "validateDateMessage", "validateDateAutoDismiss"}, requireAll = false)
    public static void bindingDate(TextView view, String pattern, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.error_message_date_validation);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new DateRule(view, pattern, handledErrorMessage));
    }

}
