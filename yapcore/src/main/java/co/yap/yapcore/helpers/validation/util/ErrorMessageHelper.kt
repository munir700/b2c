package co.yap.yapcore.helpers.validation.util

import android.view.View
import androidx.annotation.StringRes

/**
 * Created irfan arshad on 10/6/2020.
 */
object ErrorMessageHelper {
    fun getStringOrDefault(
        view: View?, errorMessage: String?,
        @StringRes defaultMessage: Int
    ): String {
        return errorMessage ?: view?.context?.getString(defaultMessage)!!
    }

    fun getStringOrDefault(
        view: View?, errorMessage: String?,
        @StringRes defaultMessage: Int, value: Int
    ): String {
        return errorMessage ?: view?.context?.getString(defaultMessage, value)!!
    }

    fun getStringOrDefault(
        view: View?, errorMessage: CharSequence?,
        @StringRes defaultMessage: Int
    ): String {
        return errorMessage?.toString() ?: view?.context?.getString(defaultMessage)!!
    }

    fun getStringOrDefault(
        view: View?, errorMessage: CharSequence?,
        @StringRes defaultMessage: Int, value: Int
    ): String {
        return errorMessage?.toString() ?: view?.context?.getString(defaultMessage, value)!!
    }
}