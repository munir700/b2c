package co.yap.yapcore.helpers.jwtparser;

import androidx.annotation.Keep;

@SuppressWarnings("WeakerAccess")
@Keep
public class DecodeException extends RuntimeException {

    DecodeException(String message) {
        super(message);
    }

    DecodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
