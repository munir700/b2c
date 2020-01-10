package co.yap.yapcore.helpers

import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import co.yap.yapcore.R
import com.google.android.material.snackbar.Snackbar

object CustomSnackbar {

    fun showErrorCustomSnackbar(
        context: Context,
        layout: CoordinatorLayout,
        message: String
    ) {
        layout.bringToFront()
        val snackbar: Snackbar?
        snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            snackbar.view.setBackgroundColor(context.getColor(R.color.errorLightBackground))
        } else {
            snackbar.view.setBackgroundColor(context.resources.getColor(R.color.errorLightBackground))
        }
        val snackbarText = snackbar.view.findViewById(R.id.snackbar_text) as TextView
        snackbarText.maxLines = 5

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            snackbarText.setTextAppearance(R.style.Micro)
        } else {
            snackbarText.setTextAppearance(context, R.style.AMicro)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            snackbarText.setTextColor(context.getColor(R.color.error))
            snackbar.setActionTextColor(context.getColor(R.color.error))
        }else{
            snackbarText.setTextColor(context.resources.getColor(R.color.error))
            snackbar.setActionTextColor(context.resources.getColor(R.color.error))
        }
        val snackBarLayout: Snackbar.SnackbarLayout =
            snackbar.view as Snackbar.SnackbarLayout
        for (i in 0 until snackBarLayout.childCount) {
            val parent = snackBarLayout.getChildAt(i)
            if (parent is LinearLayout) {
                parent.rotation = 180f
                break
            }
        }
        snackbar.show()
    }

    fun getCustomSnackbarSticky(
        context: Context,
        coordinatorLayout: CoordinatorLayout,
        message: String
    ) : Snackbar {
        coordinatorLayout.bringToFront()
        val snackbar: Snackbar?
        snackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_INDEFINITE)
        val layout = snackbar.view as Snackbar.SnackbarLayout
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val snackView = layoutInflater.inflate(R.layout.snackbar_card_status, null)

        layout.addView(snackView, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            snackbar.view.setBackgroundColor(context.getColor(R.color.colorPrimary))
        } else {
            snackbar.view.setBackgroundColor(ThemeColorUtils.colorPrimaryAttribute(context))
        }

        val tvMessage = snackbar.view.findViewById(R.id.tvMessage) as TextView
        tvMessage.text = message
        val tvAction = snackbar.view.findViewById(R.id.tvAction) as TextView
        val text = tvAction.text.toString()
        val content = SpannableString(text)
        content.setSpan(UnderlineSpan(), 0, text.length, 0)
        tvAction.text = content

        for (i in 0 until layout.childCount) {
            val parent = layout.getChildAt(i)
            if (parent is RelativeLayout) {
                parent.rotation = 180f
                break
            }
        }
        return snackbar
    }

}