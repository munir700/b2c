package co.yap.yapcore.helpers

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.toastNow
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.Snackbar

fun Activity?.showSnackBar(
    msg: String,
    gravity: Int = Gravity.BOTTOM,
    duration: Int = Snackbar.LENGTH_LONG
) {
    val snakbar = Snackbar.make(
        this?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        duration
    )

    show(snakbar, gravity)
}

fun Activity.showSnackBar(
    msg: String, @ColorRes viewBgColor: Int, @ColorRes colorOfMessage: Int,
    actionText: CharSequence,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {
    val snakbar = Snackbar.make(
        this.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        duration
    )
    snakbar.view.setBackgroundColor(ContextCompat.getColor(this, viewBgColor))
    snakbar.setTextColor(ContextCompat.getColor(this, colorOfMessage))
    val snackRootView = snakbar.view
    val snackTextView = snackRootView
        .findViewById<TextView>(R.id.snackbar_text)
    snackTextView.setTextAppearance(this, R.style.AppFontLight)
    snakbar.setAction(actionText, clickListener)
    show(snakbar, gravity)
}

fun Activity.showSnackBar(
    msg: String, @ColorRes viewBgColor: Int, @ColorRes colorOfMessage: Int,
    actionText: String,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {
    val snakbar = Snackbar.make(
        this.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        duration
    )
    snakbar.view.setBackgroundColor(ContextCompat.getColor(this, viewBgColor))
    snakbar.setTextColor(ContextCompat.getColor(this, colorOfMessage))
    val snackRootView = snakbar.view
    val snackTextView = snackRootView
        .findViewById<TextView>(R.id.snackbar_text)
    snackTextView.setTextAppearance(this, R.style.AppFontLight)
    snakbar.setAction(actionText, clickListener)
    show(snakbar, gravity)
}

// for activity and action
fun Activity.showSnackBar(
    msg: String,
    actionText: String,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {
    val snakbar = Snackbar
        .make(
            this.window.decorView.findViewById(android.R.id.content),
            validateString(msg),
            duration
        )
        .setAction(actionText, clickListener)
    show(snakbar, gravity)
}

fun Context?.showSnackBar(msg: String) {
    if (this is Activity) {
        showSnackBar(msg)
    } else {
        toastNow(msg)
    }
}

fun Fragment?.showSnackBar(
    msg: String,
    gravity: Int = Gravity.BOTTOM,
    duration: Int = Snackbar.LENGTH_LONG
) {
    val snakbar = Snackbar.make(
        this?.requireActivity()?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        duration
    )
    show(snakbar, gravity)
}

fun Fragment.showSnackBar(
    msg: String, @ColorRes viewBgColor: Int, @ColorRes colorOfMessage: Int,
    actionText: String,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {
    val snakbar = Snackbar.make(
        this.requireActivity().window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        duration
    )
    snakbar.view.setBackgroundColor(ContextCompat.getColor(this.requireActivity(), viewBgColor))
    snakbar.setTextColor(ContextCompat.getColor(this.requireActivity(), colorOfMessage))
    val snackRootView = snakbar.view
    val snackTextView = snackRootView
        .findViewById<TextView>(R.id.snackbar_text)
    snackTextView.setTextAppearance(this.requireActivity(), R.style.AppFontLight)
    snakbar.setAction(actionText, clickListener)
    show(snakbar, gravity)
}

fun Fragment.showSnackBar(
    msg: String, @ColorRes viewBgColor: Int, @ColorRes colorOfMessage: Int,
    actionText: CharSequence,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {
    val snakbar = Snackbar.make(
        this.requireActivity()?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        duration
    )
    snakbar.view.setBackgroundColor(ContextCompat.getColor(this.requireActivity(), viewBgColor))
    snakbar.setTextColor(ContextCompat.getColor(this.requireActivity(), colorOfMessage))
    val snackRootView = snakbar.view
    val snackTextView = snackRootView
        .findViewById<TextView>(R.id.snackbar_text)
    snackTextView.setTextAppearance(this.requireActivity(), R.style.AppFontLight)
    snakbar.setAction(actionText, clickListener)
    show(snakbar, gravity)
}

fun Fragment?.showSnackBar(
    msg: String, @ColorRes viewBgColor: Int, @ColorRes colorOfMessage: Int,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG
) {
    val snakbar = Snackbar.make(
        this?.requireActivity()?.window?.decorView?.findViewById(android.R.id.content)!!,
        validateString(msg),
        duration
    )
    snakbar.view.setBackgroundColor(ContextCompat.getColor(this?.requireContext()!!, viewBgColor))
    snakbar.setTextColor(ContextCompat.getColor(this.requireContext(), colorOfMessage))
    val snackRootView = snakbar.view
    val snackTextView = snackRootView
        .findViewById<TextView>(R.id.snackbar_text)
    snackTextView.setTextAppearance(context, R.style.AppFontLight)
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
fun Fragment?.showSnackBar(
    msg: String,
    actionText: String,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {
    val snackbar = Snackbar
        .make(
            this?.requireActivity()?.window?.decorView?.findViewById(android.R.id.content)!!,
            validateString(msg),
            duration
        )
        .setAction(actionText, clickListener)
    show(snackbar, gravity)
}


// for styling view and action color action
fun View?.showSnackBar(
    viewBgColor: Int,
    colorOfMessage: Int,
    snackBarMsg: String,
    isCapsMesg: Boolean,
    messageSize: Int,
    actionTextColor: Int,
    actionText: String, gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {

    val snackbar = Snackbar.make(this!!, validateString(snackBarMsg), duration)
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

fun View?.showSnackBar(
    msg: String, @ColorRes viewBgColor: Int, @ColorRes colorOfMessage: Int,
    actionText: CharSequence,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {
    this?.let {
        val snakbar = Snackbar.make(
            it,
            "",
            duration
        )
        val layout = snakbar.view as Snackbar.SnackbarLayout
        val layoutInflater: LayoutInflater = LayoutInflater.from(it.context)
        val snackView = layoutInflater.inflate(R.layout.snackbar_card_status, null)
        layout.addView(snackView,0)
        snakbar.view.setBackgroundColor(ContextCompat.getColor(it.context, viewBgColor))
        val tvMessage = layout.findViewById(R.id.tvMessage) as TextView
        tvMessage.text = validateString(msg)
        val tvAction = layout.findViewById(R.id.tvAction) as TextView
        tvAction.text = actionText
        tvAction.setOnClickListener(clickListener)

//        snakbar.setTextColor(ContextCompat.getColor(it.context, colorOfMessage))
//        val snackRootView = snakbar.view
//        val snackTextView = snackRootView
//            .findViewById<TextView>(R.id.snackbar_text)
//        snackTextView.setTextAppearance(R.style.AppFontLight)
//        snakbar.setActionTextColor(ContextCompat.getColor(it.context, colorOfMessage))
//        snakbar.setAction(actionText, clickListener)
        show(snakbar, gravity)
    }

}

private fun show(snakbar: Snackbar, gravity: Int = Gravity.BOTTOM) {
    val view = snakbar.view

    when(view.layoutParams){
        is CoordinatorLayout.LayoutParams-> {
            val param = view.layoutParams as CoordinatorLayout.LayoutParams
            param.gravity = gravity
            view.layoutParams = param
        }
        is LinearLayout.LayoutParams-> {
            val param = view.layoutParams as LinearLayout.LayoutParams
            param.gravity = gravity
        }
        else->{
            val param = view.layoutParams as FrameLayout.LayoutParams
            param.gravity = gravity
            view.layoutParams = param
        }
    }

    if (view.layoutParams is CoordinatorLayout.LayoutParams) {
        val param = view.layoutParams as CoordinatorLayout.LayoutParams
        param.gravity = gravity
        view.layoutParams = param
    }
    snakbar.animationMode = ANIMATION_MODE_FADE

//    val fadeIn = AlphaAnimation(0f, 1f)
//    fadeIn.interpolator = DecelerateInterpolator() //add this
//    fadeIn.duration = 1000
//
//    val fadeOut = AlphaAnimation(1f, 0f)
//    fadeOut.interpolator = AccelerateInterpolator() //and this
//    fadeOut.startOffset = 1000
//    fadeOut.duration = 1000
//
//    val animation = AnimationSet(false) //change to false
//    animation.addAnimation(fadeIn)
//    animation.addAnimation(fadeOut)
//    view.animation = animation
    SnackBarQueue.snackBarQueue.add(snakbar)
    snakbar.show()
}

private fun View.findSuitableLayoutParams() = when(this.layoutParams) {
        is CoordinatorLayout.LayoutParams-> this.layoutParams as CoordinatorLayout.LayoutParams
        is LinearLayout.LayoutParams-> this.layoutParams as LinearLayout.LayoutParams
        else-> this.layoutParams as LinearLayout.LayoutParams
    }



fun cancelAllSnackBar() =
    SnackBarQueue.cancelSnackBars()

// for view and action
fun View?.showSnackBar(
    msg: String,
    actionText: String,
    gravity: Int = Gravity.BOTTOM, duration: Int = Snackbar.LENGTH_LONG,
    clickListener: View.OnClickListener
) {
    val snakbar = Snackbar
        .make(this!!, validateString(msg), duration)
        .setAction(actionText, clickListener)
    show(snakbar, gravity)
}

fun validateString(msg: String?): String {
    return msg ?: "null"
}

private object SnackBarQueue {
    val snackBarQueue = mutableListOf<Snackbar>()

    fun cancelSnackBars() {
        snackBarQueue.forEach { it.dismiss() }
        snackBarQueue.clear()
    }

    fun removeSnackBar(snackBar: Snackbar) = snackBarQueue.remove(snackBar)

}