package co.yap.widgets.guidedtour.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.RectF
import co.yap.widgets.guidedtour.target.Target


class Rect : Shape {
    var adjustedRect: RectF? = null

    constructor(target: Target?) : super(target!!) {
        calculateAdjustedRect()
    }

    constructor(target: Target?, focus: Focus?) : super(
        target!!,
        focus
    ) {
        calculateAdjustedRect()
    }

    constructor(
        target: Target?,
        focus: Focus?,
        focusGravity: FocusGravity?,
        padding: Int
    ) : super(target!!, focus, focusGravity!!, padding) {
        calculateAdjustedRect()
    }

    override fun draw(
        canvas: Canvas?,
        eraser: Paint?,
        padding: Int
    ) {
        canvas!!.drawRoundRect(adjustedRect, padding.toFloat(), padding.toFloat(), eraser)
    }

    private fun calculateAdjustedRect() {
        val rect = RectF()
        rect.set(target.getRect())
        rect.left -= padding.toFloat()
        rect.top -= padding.toFloat()
        rect.right += padding.toFloat()
        rect.bottom += padding.toFloat()
        adjustedRect = rect
    }

    override fun reCalculateAll() {
        calculateAdjustedRect()
    }

    override val point: Point
        get() = target.getPoint()

    override val height: Int
        get() = adjustedRect!!.height().toInt()

    override fun isTouchOnFocus(x: Double, y: Double): Boolean {
        return adjustedRect!!.contains(x.toFloat(), y.toFloat())
    }
}
