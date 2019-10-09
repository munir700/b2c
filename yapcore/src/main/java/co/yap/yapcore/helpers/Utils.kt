package co.yap.yapcore.helpers

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import co.yap.yapcore.R
import java.text.DecimalFormat
import android.icu.lang.UProperty.INT_START



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

    fun getFormattedCurrency(num: String?): String {
        return if ("" != num && null != num) {
            val m = java.lang.Double.parseDouble(num)
            val formatter = DecimalFormat("###,###,##0.00")
            formatter.format(m)
        } else {
            ""
        }
    }

    fun getFormattedCurrencyWithoutComma(num: String?): String {
        return if ("" != num && null != num) {
            val m = java.lang.Double.parseDouble(num)
            val formatter = DecimalFormat("########0.00")
            formatter.format(m)
        } else {
            ""
        }
    }

    fun convertDpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    }

    fun convertPxToDp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    }

    fun getNavigationBarHeight(activity: Activity): Int {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val usableHeight = metrics.heightPixels
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)
        val realHeight = metrics.heightPixels
        return if (realHeight > usableHeight)
            realHeight - usableHeight
        else
            0
    }

    fun getCardDimensions(context: Context, width: Int, height: Int): IntArray {
        val dimensions = IntArray(2)
        dimensions[0] = getDimensionInPercent(context, true, width)
        dimensions[1] = getDimensionInPercent(context, false, height)
        return dimensions
    }

    fun getDimensionInPercent(context: Context, isWidth: Boolean, percent: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return if (isWidth) {
            ((displayMetrics.widthPixels.toDouble() / 100) * percent).toInt()
        } else {
            if (hasNavBar(context.resources)) {
                //val h = getNavBarHeight(context.resources)
                //val bottom = convertDpToPx(context, 56f)
                (((displayMetrics.heightPixels.toDouble() - getNavBarHeight(context.resources)) / 100) * percent).toInt()
            } else {
                ((displayMetrics.heightPixels.toDouble() / 100) * percent).toInt()
            }
        }
    }

    private fun hasNavBar(resources: Resources): Boolean {
        val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return id > 0 && resources.getBoolean(id)
    }

    private fun getNavBarHeight(resources: Resources): Int {

        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)
    }

    fun setSpan(
        startIndex: Int,
        endIndex: Int,
        wordtoSpan: SpannableString,
        color: Int
    ): SpannableString {
         wordtoSpan.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        wordtoSpan.setSpan(
            android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return wordtoSpan
    }

     fun shortName(cardFullName: String): String {
        var cardFullName = cardFullName
        cardFullName = cardFullName.trim { it <= ' ' }
        var shortName = ""
        if (cardFullName.isNotEmpty() && cardFullName.contains(" ")) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            val lastName = nameStr[nameStr.size-1]
            shortName = firstName.substring(0, 1) + lastName.substring(0, 1)
            return shortName.toUpperCase()
        } else if (cardFullName.length > 0) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            shortName = firstName.substring(0, 1)
            return shortName.toUpperCase()
        }
        return shortName.toUpperCase()
    }

}