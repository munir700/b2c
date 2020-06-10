package co.yap.yapcore.helpers.validation.rule;

import android.view.View;
import android.widget.TextView;

import co.yap.yapcore.helpers.validation.util.EditTextHandler;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class UsernameRule extends TypeRule {

    public UsernameRule(TextView view, String errorMessage) {
        super(view, FieldType.Username, errorMessage);
    }

    @Override
    protected boolean isValid(TextView view) {
        String username = view.getText().toString();
        return view.getVisibility() == View.GONE || username.matches("[a-zA-Z0-9-._]+");
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
