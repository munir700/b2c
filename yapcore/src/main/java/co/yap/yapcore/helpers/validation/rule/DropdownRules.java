package co.yap.yapcore.helpers.validation.rule;

import android.widget.TextView;

import co.yap.yapcore.helpers.validation.util.EditTextHandler;


/**
 * Created irfan arshad on 10/6/2020.
 */
public class DropdownRules extends Rule<TextView, Boolean> {

    public DropdownRules(TextView view, Boolean value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {

        final String value1 = view.getText().toString();
        return !value1.equals("Select Province");
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
