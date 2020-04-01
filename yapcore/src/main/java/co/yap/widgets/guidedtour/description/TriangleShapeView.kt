package co.yap.widgets.guidedtour.description

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


class TriangleShapeView : View {
    var colorCode = Color.MAGENTA

    constructor(context: Context?) : super(context) {}
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width / 2
        val h = height / 2
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(w.toFloat(), 2 * h.toFloat())
        path.lineTo(2 * w.toFloat(), 0f)
        path.lineTo(0f, 0f)
        path.close()
        val p = Paint()
        p.color = colorCode
        p.isAntiAlias = true
        canvas.drawPath(path, p)
    }
}