package co.yap.yapcore.helpers.validation.rule;

import android.view.View;

/**
 * Created irfan arshad on 10/6/2020.
 */
public abstract class Rule<ViewType extends View, ValueType> {

    protected ValueType value;
    protected ViewType view;
    protected String errorMessage;

    public Rule(ViewType view, ValueType value, String errorMessage) {
        this.view = view;
        this.value = value;
        this.errorMessage = errorMessage;
    }

    public final boolean validate() {
        final boolean valid = isValid(view);
        if (valid) {
            onValidationSucceeded(view);
        } else {
            onValidationFailed(view);
        }
        return valid;
    }

    protected abstract boolean isValid(ViewType view);

    protected void onValidationSucceeded(ViewType view) {
    }

    protected void onValidationFailed(ViewType view) {
    }

    public ViewType getView() {
        return view;
    }
}
