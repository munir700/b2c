package co.yap.modules.otp

import android.content.Context
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.R
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.SessionManager


fun Context?.getOtpMessageFromComposer(otpActions: String, vararg args: Any?): String {
    return this?.let { context ->
        context.resources.getStringArray(R.array.screen_otp_messages).map {
            it.split(";")[0] to it.split(";")[1]
        }.toMap()[otpActions]?.format(*args)
    } ?: ""
}