package co.yap.yapcore.helpers.validation.rule;

import android.view.View;
import android.widget.TextView;

import co.yap.yapcore.helpers.validation.util.EditTextHandler;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class MaxLengthRule extends Rule<TextView, Integer> {

    public MaxLengthRule(TextView view, Integer value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {
        return view.getVisibility() == View.GONE || view.length() <= value;
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
