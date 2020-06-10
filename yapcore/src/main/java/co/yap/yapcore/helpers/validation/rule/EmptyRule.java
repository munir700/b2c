package co.yap.yapcore.helpers.validation.rule;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import co.yap.yapcore.helpers.validation.util.EditTextHandler;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class EmptyRule extends Rule<TextView, Boolean> {

    public EmptyRule(TextView view, Boolean value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {
        if (view.getVisibility() == View.GONE) return true;
        return !value || !TextUtils.isEmpty(view.getText());
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
