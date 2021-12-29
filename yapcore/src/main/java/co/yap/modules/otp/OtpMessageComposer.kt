package co.yap.modules.otp

import android.content.Context
import co.yap.yapcore.R


fun Context?.getOtpMessageFromComposer(otpActions: String, vararg args: Any?): String {
    return this?.let { context ->
        context.resources.getStringArray(R.array.screen_otp_messages).map {
            it.split(";")[0] to it.split(";")[1]
        }.toMap()[otpActions]?.format(*args)
    } ?: ""
}