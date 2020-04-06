package co.yap.widgets.guidedtour.description

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


class TriangleShapeView : View {
    var colorCode = Color.RED

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
//        val w = 60
//        val h = 60
//        val path = Path()
//        path.moveTo(0f, 0f)
//        path.lineTo(w.toFloat(), 2 * h.toFloat())
//        path.lineTo(2 * w.toFloat(), 0f)
//        path.lineTo(0f, 0f)
//        path.close()
//        val p = Paint()
//        p.color = colorCode
//        p.isAntiAlias = true
//        canvas.drawPath(path, p)


        //


        val w = 60
        val h = 60
        val mPath = Path()
        val p = Paint()
        p.color = colorCode
        p.isAntiAlias = true


        mPath.moveTo(w.toFloat(), 0f)
        mPath.lineTo(w.toFloat(), 0f)
        mPath.lineTo((w / 2).toFloat(), h.toFloat())
        mPath.lineTo(0f, 0f)
        mPath.close()
        canvas.drawPath(mPath, p)
    }
}