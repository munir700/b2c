package com.ezaka.customer.app.utils

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.text.ClipboardManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull


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
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun Context.hideSoftKeyboard() {
    try {
        val activity = getActivityFromContext(this)
        activity?.hideKeyboard()

    } catch (ignored: Exception) {
    }

}
fun getActivityFromContext(@NonNull context: Context): Activity? {
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

fun String.copyTextToClipboard(context: Context){
    val clipboard =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", this)
    clipboard.setPrimaryClip(clip)
}