package com.yap.core.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Insets
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

const val LDPI: Int = DisplayMetrics.DENSITY_LOW
const val MDPI: Int = DisplayMetrics.DENSITY_MEDIUM
const val HDPI: Int = DisplayMetrics.DENSITY_HIGH

const val TVDPI: Int = DisplayMetrics.DENSITY_TV
const val XHDPI: Int = DisplayMetrics.DENSITY_XHIGH
const val XXHDPI: Int = DisplayMetrics.DENSITY_XXHIGH
const val XXXHDPI: Int = DisplayMetrics.DENSITY_XXXHIGH

//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()


/**
 * convert dip to px
 *
 * @param[value] to convert
 * @return calculated dip
 */
fun Context.dip2px(value: Int): Int = (value * resources.displayMetrics.density).toInt()

/**
 * convert dip to px
 *
 * @param[value] to convert
 * @return calculated dip
 * @since 1.0.1
 */
fun Context.dip2px(value: Float): Float = (value * resources.displayMetrics.density)

/**
 * convert sp to px
 *
 * @param[value] to convert
 * @return calculated sp
 */
fun Context.sp2px(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()


/**
 * convert sp to px
 *
 * @param[value] to convert
 * @return calculated sp
 */
fun Context.sp2px(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()


/**
 * convert px to dip
 *
 * @param[px] to convert
 * @return calculated dip
 */
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density

/**
 * convert px to sp
 *
 * @param[px] to convert
 * @return calculated sp
 */
fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

/**
 * get pixel size from DimenRes
 *
 * @param[resource] dimen res to convert
 * @return proper pixel size
 */
fun Context.dimen(resource: Int): Int = resources.getDimensionPixelSize(resource)
/* Functions for Conversions */

fun Fragment.dimen(resource: Int) = requireContext().dimen(resource)

/** gets display size as a point. */
internal fun Context.displaySize(): Point {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

/** gets a color from the resource. */
internal fun Context.contextColor(resource: Int): Int {
    return ContextCompat.getColor(this, resource)
}

/** gets a drawable from the resource. */
internal fun Context.contextDrawable(resource: Int): Drawable? {
    return ContextCompat.getDrawable(this, resource)
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun Context.getDimensionsByPercentage(width: Int, height: Int): IntArray {
    val dimensions = IntArray(2)
    dimensions[0] = getDimensionInPercent(this, true, width)
    dimensions[1] = getDimensionInPercent(this, false, height)
    return dimensions
}

fun getDimensionInPercent(context: Context, isWidth: Boolean, percent: Int): Int {
    val displayMetrics = context.resources.displayMetrics
    return if (isWidth) {
        ((displayMetrics.widthPixels.toDouble() / 100) * percent).toInt()
    } else {
        if (context.resources.hasNavBar()) {
            //val h = getNavBarHeight(context.resources)
            //val bottom = convertDpToPx(context, 56f)
            (((displayMetrics.heightPixels.toDouble() - context.resources.getNavBarHeight()) / 100) * percent).toInt()
        } else {
            ((displayMetrics.heightPixels.toDouble() / 100) * percent).toInt()
        }
    }
}
/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun Float.toDp(context: Context): Float {
    return this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Float.toPx(context: Context): Int {
    return (this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

/**
 * Get app window width.
 *
 * @return screen width in pixels.
 */
fun Window.getAppWindowWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = this.windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

/**
 * Fix width if the screen is small then the layout width.
 *
 * @param layoutWidthInDp Layout width in dp.
 * @param layoutTotalHorizontalMarginInDp Layout horizontal total margin in dp.
 */
fun Window.fixWidth(layoutWidthInDp: Int, layoutTotalHorizontalMarginInDp: Int) {
    val widthDp = getAppWindowWidth().toFloat().toDp(context)
    // Check if the dialog width is bigger then the screen width!
    if (widthDp < (layoutWidthInDp + layoutTotalHorizontalMarginInDp)) {
        setLayout(
            (widthDp - layoutTotalHorizontalMarginInDp).toPx(context),
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    } else {
        setLayout(
            layoutWidthInDp.toFloat().toPx(context),
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }
}
