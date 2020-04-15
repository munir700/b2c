package co.yap.widgets.couchmark

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager


object ScreenUtils {

    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun getAxisXpositionOfViewOnScreen(targetView: View): Int {
        val locationTarget = IntArray(2)
        targetView.getLocationOnScreen(locationTarget)
        return locationTarget[0]
    }

    fun getAxisYpositionOfViewOnScreen(targetView: View): Int {
        val locationTarget = IntArray(2)
        targetView.getLocationOnScreen(locationTarget)
        return locationTarget[1]
    }


    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun dpToPx(dp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        return Math.round(dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun isViewLocatedAtHalfTopOfTheScreen(context: Context, targetView: View): Boolean {
        val screenHeight = getScreenHeight(context)
        val positionTargetAxisY = getAxisYpositionOfViewOnScreen(targetView)
        return screenHeight / 2 > positionTargetAxisY
    }

    fun isViewLocatedAtBottomOfTheScreen(
        activity: Context,
        targetView: View,
        threshold: Int
    ): Boolean {
        val screenHeight = getScreenHeight(activity)
        val positionTargetAxisY = getAxisYpositionOfViewOnScreen(targetView)
        return (screenHeight - threshold) < positionTargetAxisY
    }

    fun isViewLocatedAtTopOfTheScreen(
        activity: Context,
        targetView: View,
        threshold: Int
    ): Boolean {
        val screenHeight = getScreenHeight(activity)
        val positionTargetAxisY = getAxisYpositionOfViewOnScreen(targetView)
        return (positionTargetAxisY - threshold) < 0
    }

    fun isViewLocatedAtHalfLeftOfTheScreen(activity: Activity, targetView: View): Boolean {
        val screenWidth = getScreenWidth(activity)
        val positionTargetAxisX = getAxisXpositionOfViewOnScreen(targetView)
        return screenWidth / 2 > positionTargetAxisX
    }
}