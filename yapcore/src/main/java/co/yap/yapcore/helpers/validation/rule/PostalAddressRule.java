package co.yap.yapcore.helpers.validation.rule;

import android.widget.TextView;

import co.yap.yapcore.helpers.validation.util.EditTextHandler;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class PostalAddressRule extends TypeRule {

    public PostalAddressRule(TextView view, String errorMessage) {
        super(view, FieldType.Username, errorMessage);
    }

    @Override
    protected boolean isValid(TextView view) {
        String postalAddress = view.getText().toString();
        return postalAddress.matches("\\\\d+\\\\s+([a-zA-Z]+|[a-zA-Z]+\\\\s[a-zA-Z]+)");
    }

    @Override
    protected void onValidationSucceeded(TextView view) {
        super.onValidationSucceeded(view);
        EditTextHandler.removeError(view);
    }

    @Override
    protected void onValidationFailed(TextView view) {
        super.onValidationFailed(view);
        EditTextHandler.setError(view, errorMessage);
    }
}
