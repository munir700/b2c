package co.yap.yapcore.helpers

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import co.yap.yapcore.R

object Utils {
    fun getColor(context: Context, @ColorRes color: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(color, null)
        } else {
            context.resources.getColor(color)
        }

    fun requestKeyboard(view: View, request: Boolean, forced: Boolean) {
        view.requestFocus()
        if (forced) {
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        } else if (request) {
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

    }

    fun hideKeyboard(view: View?) {
        view?.let { v ->
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun createProgressDialog(context: Context): AlertDialog {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.progress_dialogue_fragment, null)
        view.findViewById<ProgressBar>(R.id.progressBar2).indeterminateDrawable.setColorFilter(
            getColor(
                context,
                R.color.colorPrimaryDark
            ), android.graphics.PorterDuff.Mode.SRC_IN
        )
        return AlertDialog.Builder(context).run {
            setView(view)
            setCancelable(false)
            create()
        }.apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
    }

    fun copyToClipboard(context: Context, text: CharSequence) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("YapAccount", text)
        clipboard.primaryClip = clip
    }


}