package co.yap.yapcore.helpers.validation.rule;

import android.widget.TextView;

import co.yap.yapcore.helpers.validation.util.EditTextHandler;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class RegexRule extends Rule<TextView, String> {

    public RegexRule(TextView view, String value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {
        return view.getText().toString().matches(value);
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
