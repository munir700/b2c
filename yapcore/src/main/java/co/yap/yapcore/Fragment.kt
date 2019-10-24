package co.yap.yapcore

/**
 * This file contains the various extension functions from core library that requires context
 * That should also be called from the fragment directly without explicitly calling on context
 */

import android.widget.Toast
import androidx.fragment.app.Fragment
import co.yap.yapcore.helpers.cancelAndMakeToast
import co.yap.yapcore.helpers.makeToast


/* Functions for toast */

fun Fragment?.toast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.shortToast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_SHORT)
fun Fragment?.longToast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.toastNow(msg: String) = cancelAndMakeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.shortToastNow(msg: String) =
    cancelAndMakeToast(this?.context, msg, Toast.LENGTH_SHORT)

fun Fragment?.longToastNow(msg: String) = cancelAndMakeToast(this?.context, msg, Toast.LENGTH_LONG)



