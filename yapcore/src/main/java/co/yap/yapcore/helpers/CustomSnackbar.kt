package co.yap.yapcore.helpers

import android.content.Context
import android.os.Build
import android.widget.LinearLayout
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
        val snackbar: Snackbar?
        layout.bringToFront()
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
}