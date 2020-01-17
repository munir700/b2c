package co.yap.yapcore.helpers

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.yap.yapcore.R
import com.google.android.material.snackbar.Snackbar

fun Activity?.showSnackBar(msg: String, gravity: Int = Gravity.BOTTOM) {
    val snakbar = Snackbar.make(
        this?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        Snackbar.LENGTH_LONG
    )

    show(snakbar, gravity)
}

fun Activity?.show1(msg: String) {
    val snakbar = Snackbar.make(
        this?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        Snackbar.LENGTH_LONG
    )
    show(snakbar)
}

// for activity and action
fun Activity.show(
    msg: String,
    actionText: String,
    gravity: Int = Gravity.BOTTOM,
    clickListener: View.OnClickListener
) {
    val snakbar = Snackbar
        .make(
            this.window.decorView.findViewById(android.R.id.content),
            validateString(msg),
            Snackbar.LENGTH_LONG
        )
        .setAction(actionText, clickListener)
    show(snakbar)
}

fun Context?.showSnackBar(msg: String) {
    if (this is Activity) {
        showSnackBar(msg)
    } else {
        toastNow(msg)
    }
}

fun Fragment?.showSnackBar(msg: String, gravity: Int = Gravity.BOTTOM) {
    val snakbar = Snackbar.make(
        this?.requireActivity()?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        Snackbar.LENGTH_LONG
    )
    show(snakbar)
}

fun Fragment?.showSnackBar(
    msg: String, @ColorRes viewBgColor: Int, @ColorRes colorOfMessage: Int,
    gravity: Int = Gravity.BOTTOM
) {
    val snakbar = Snackbar.make(
        this?.requireActivity()?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        Snackbar.LENGTH_LONG
    )
    snakbar.view.setBackgroundColor(ContextCompat.getColor(this?.requireContext()!!, viewBgColor))
    snakbar.setTextColor(ContextCompat.getColor(this.requireContext(), colorOfMessage))
    val snackRootView = snakbar.view
    val snackTextView = snackRootView
        .findViewById<TextView>(R.id.snackbar_text)
    snackTextView.setTextAppearance(context,R.style.AppFontLight)
    show(snakbar, gravity)
}

fun Fragment?.show1(msg: String, gravity: Int = Gravity.BOTTOM) {
    val snakbar = Snackbar.make(
        this?.requireActivity()?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        Snackbar.LENGTH_LONG
    )
    show(snakbar, gravity)
}

// for activity and action
fun Fragment?.show(
    msg: String,
    actionText: String,
    gravity: Int = Gravity.BOTTOM,
    clickListener: View.OnClickListener
) {
    val snackbar = Snackbar
        .make(
            this?.requireActivity()?.window?.decorView?.findViewById(android.R.id.content)!!,
            validateString(msg),
            Snackbar.LENGTH_LONG
        )
        .setAction(actionText, clickListener)
    show(snackbar)
}


// for styling view and action color action
fun View?.showSnackBar(
    viewBgColor: Int,
    colorOfMessage: Int,
    snackBarMsg: String,
    isCapsMesg: Boolean,
    messageSize: Int,
    actionTextColor: Int,
    actionText: String, gravity: Int = Gravity.BOTTOM,
    clickListener: View.OnClickListener
) {

    val snackbar = Snackbar.make(this!!, validateString(snackBarMsg), Snackbar.LENGTH_LONG)
    val snackbarView: View = snackbar.view

    // styling for rest of text

    /* val textView : TextView = snackbarView.findViewById(android.support.design.R.id.snackbar_text)
     textView.setTextColor(colorOfMessage)
     textView.setAllCaps(isCapsMesg)
     textView.setTextSize((if (messageSize < 10) 20 else messageSize).toFloat())*/


    // styling for background of snackbar

    snackbarView.setBackgroundColor(viewBgColor)
    //styling for action of text
    snackbar.setActionTextColor(actionTextColor)
    snackbar.setAction(actionText, clickListener)
    show(snackbar, gravity)
}

private fun show(snakbar: Snackbar, gravity: Int = Gravity.BOTTOM) {
    val view = snakbar.view

    val param = view.layoutParams as FrameLayout.LayoutParams
    param.gravity = gravity
    view.layoutParams = param
    snakbar.show()
}

// for view and action
fun View?.showSnackBar(
    msg: String,
    actionText: String,
    gravity: Int = Gravity.BOTTOM,
    clickListener: View.OnClickListener
) {
    val snakbar = Snackbar
        .make(this!!, validateString(msg), Snackbar.LENGTH_LONG)
        .setAction(actionText, clickListener)
    show(snakbar, gravity)
}

fun validateString(msg: String?): String {
    return msg ?: "null"
}

private object SnackBarQueue {
    val snackBarQueue = mutableListOf<Toast>()

    fun cancelSnackBars() {
        snackBarQueue.forEach { it.cancel() }
        snackBarQueue.clear()
    }

    fun removeSnackBar(toast: Toast) = snackBarQueue.remove(toast)

}