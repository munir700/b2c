@file:JvmName("KeyboardUtils")

package co.yap.yapcore.helpers.extentions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment


/**
 * Hides the soft keyboard
 * @receiver Activity
 * @return a boolean value if the action was performed or not
 */
fun Activity.hideKeyboard(): Boolean {
    val view = currentFocus
    view?.let {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    return false
}


/**
 * Opens up the keyboard by focusing on the view
 * @receiver View
 */
fun View.showKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Opens up the keyboard forcefully
 * @receiver Context
 */
fun Context.showKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View?.hideKeyboard(): Boolean {
    this?.let {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
    }
    return false
}

fun Context.hideKeyboard() {
    try {
        val activity =
            getActivityFromContext(this)
        activity?.hideKeyboard()

    } catch (ignored: Exception) {
    }

}

private fun getActivityFromContext(@NonNull context: Context): Activity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context
            .baseContext
    }
    return null
}

/**
 * Extension method to provide hide keyboard for [Fragment].
 */
fun Fragment.hideKeyboard() {
    requireActivity().hideKeyboard()
}

/**
 * Extension method to provide hide keyboard for [Fragment].
 */
fun Fragment.showKeyboard() {
    requireActivity().showKeyboard()
}