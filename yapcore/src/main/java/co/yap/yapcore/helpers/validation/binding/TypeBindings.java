package co.yap.yapcore.helpers.validation.binding;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import co.yap.yapcore.R;
import co.yap.yapcore.helpers.validation.rule.TypeRule;
import co.yap.yapcore.helpers.validation.util.EditTextHandler;
import co.yap.yapcore.helpers.validation.util.ErrorMessageHelper;
import co.yap.yapcore.helpers.validation.util.ViewTagHelper;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class TypeBindings {

    @BindingAdapter(value = {"validateType", "validateTypeMessage", "validateTypeAutoDismiss"}, requireAll = false)
    public static void bindingTypeValidation(TextView view, String fieldTypeText, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }
        TypeRule.FieldType fieldType = getFieldTypeByText(fieldTypeText);
        try {
            String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                    errorMessage, fieldType.errorMessageId);
            ViewTagHelper.appendValue(R.id.validator_rule, view, fieldType.instantiate(view, handledErrorMessage));
        } catch (Exception ignored) {
        }
    }

    @NonNull
    private static TypeRule.FieldType getFieldTypeByText(String fieldTypeText) {
        TypeRule.FieldType fieldType = TypeRule.FieldType.None;
        for (TypeRule.FieldType type : TypeRule.FieldType.values()) {
            if (type.toString().equalsIgnoreCase(fieldTypeText)) {
                fieldType = type;
                break;
            }
        }
        return fieldType;
    }

}
