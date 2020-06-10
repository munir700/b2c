package co.yap.yapcore.helpers.validation.rule;

import android.widget.TextView;

import co.yap.yapcore.helpers.validation.util.DateValidator;
import co.yap.yapcore.helpers.validation.util.EditTextHandler;


/**
 * Created irfan arshad on 10/6/2020.
 */
public class DateRule extends Rule<TextView, String> {

    private final DateValidator dateValidator;

    public DateRule(TextView view, String value, String errorMessage) {
        super(view, value, errorMessage);
        dateValidator = new DateValidator();
    }

    @Override
    public boolean isValid(TextView view) {
        return dateValidator.isValid(view.getText().toString(), value);
    }

    @Override
    public void onValidationSucceeded(TextView view) {
        EditTextHandler.removeError(view);
    }

    @Override
    public void onValidationFailed(TextView view) {
        EditTextHandler.setError(view, errorMessage);
    }
}
