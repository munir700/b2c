package co.yap.widgets.guidedtour.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import co.yap.widgets.guidedtour.Constants
import co.yap.widgets.guidedtour.target.Target


abstract class Shape(
    protected var target:Target ,
    protected var focus: Focus?,
    protected var focusGravity: FocusGravity,
    protected var padding: Int
) {

    constructor(target: Target) : this(target, Focus.MINIMUM) {}
    constructor(target: Target, focus: Focus?) : this(
        target,
        focus,
        FocusGravity.CENTER,
        Constants.DEFAULT_TARGET_PADDING
    ) {
    }

    abstract fun draw(
        canvas: Canvas?,
        eraser: Paint?,
        padding: Int
    )

    protected val focusPoint: Point
        protected get() = if (focusGravity == FocusGravity.LEFT) {
            val xLeft: Int =
                target.getRect().left + (target.getPoint().x - target.getRect().left) / 2
            Point(xLeft, target.getPoint().y)
        } else if (focusGravity == FocusGravity.RIGHT) {
            val xRight: Int =
                target.getPoint().x + (target.getRect().right - target.getPoint().x) / 2
            Point(xRight, target.getPoint().y)
        } else target.getPoint()

    abstract fun reCalculateAll()
    abstract val point: Point?
    abstract val height: Int

    /**
     * Determines if a click is on the shape
     * @param x x-axis location of click
     * @param y y-axis location of click
     * @return true if click is inside shape
     */
    abstract fun isTouchOnFocus(x: Double, t: Double): Boolean

}
