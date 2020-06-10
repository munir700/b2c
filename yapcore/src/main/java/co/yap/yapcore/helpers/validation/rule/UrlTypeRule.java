package co.yap.yapcore.helpers.validation.rule;

import android.webkit.URLUtil;
import android.widget.TextView;

import co.yap.yapcore.helpers.validation.util.EditTextHandler;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class UrlTypeRule extends TypeRule {

    public UrlTypeRule(TextView textView, String errorMessage) {
        super(textView, FieldType.Url, errorMessage);
    }

    @Override
    protected boolean isValid(TextView view) {
        return URLUtil.isValidUrl(view.getText().toString());
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
