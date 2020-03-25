package co.yap.widgets.guidedtour.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import co.yap.widgets.guidedtour.target.Target


class Circle(
    target: Target?,
    focus: Focus?,
    focusGravity: FocusGravity?,
    padding: Int,
    point: Point
) :
    Shape(target!!, focus, focusGravity!!, padding) {
    private var radius = 0
    override var point: Point = point
        private set

    override fun draw(
        canvas: Canvas?,
        eraser: Paint?,
        padding: Int
    ) {
        calculateRadius(padding)
        point = focusPoint
        canvas!!.drawCircle(
            point.x.toFloat(),
            point.y.toFloat(),
            radius.toFloat(),
            eraser
        )
    }

    override fun reCalculateAll() {
        calculateRadius(padding)
        point = focusPoint
    }

    private fun calculateRadius(padding: Int) {
        val side: Int
        if (focus === Focus.MINIMUM) side = Math.min(
            target.getRect().width() / 2,
            target.getRect().height() / 2
        ) else if (focus === Focus.ALL) side =
            Math.max(target.getRect().width() / 2, target.getRect().height() / 2) else {
            val minSide: Int =
                Math.min(target.getRect().width() / 2, target.getRect().height() / 2)
            val maxSide: Int =
                Math.max(target.getRect().width() / 2, target.getRect().height() / 2)
            side = (minSide + maxSide) / 2
        }
        radius = side + padding
    }

    override val height: Int
        get() = 2 * radius

    override fun isTouchOnFocus(x: Double, y: Double): Boolean {
        val xV = point.x
        val yV = point.y
        val dx = Math.pow(x - xV, 2.0)
        val dy = Math.pow(y - yV, 2.0)
        return dx + dy <= Math.pow(radius.toDouble(), 2.0)
    }

    init {
        point = focusPoint
        calculateRadius(padding)
    }
}