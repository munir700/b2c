package co.yap.yapcore.helpers.validation.util;

import android.view.View;

import androidx.annotation.StringRes;

/**
 * Created irfan arshad on 10/6/2020.
 */
public class ErrorMessageHelper {

    public static String getStringOrDefault(View view, String errorMessage,
                                            @StringRes int defaultMessage) {
        return errorMessage != null ? errorMessage : view.getContext().getString(defaultMessage);
    }

    public static String getStringOrDefault(View view, String errorMessage,
                                            @StringRes int defaultMessage, int value) {
        return errorMessage != null ? errorMessage : view.getContext().getString(defaultMessage, value);
    }

    public static String getStringOrDefault(View view, CharSequence errorMessage,
                                            @StringRes int defaultMessage) {
        return errorMessage != null ? errorMessage.toString() : view.getContext().getString(defaultMessage);
    }

    public static String getStringOrDefault(View view, CharSequence errorMessage,
                                            @StringRes int defaultMessage, int value) {
        return errorMessage != null ? errorMessage.toString() : view.getContext().getString(defaultMessage, value);
    }
}
