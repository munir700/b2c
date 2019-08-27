package co.yap.modules.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import co.yap.R
import kotlin.math.roundToInt


//class ChartView
class ChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    internal var paint: Paint

    private var btnWeight: Int = 50
    private var btnHeight: Int = 400
    private var roundRadius: Int = 20
    private var btnRadius: Int = 0

    init {
        paint = Paint()
    }


    @SuppressLint("ResourceType")
    protected override fun onDraw(canvas: Canvas) {
        paint.setStyle(Paint.Style.FILL)
        paint.setStrokeWidth(10F)

        var rectF: RectF = RectF()

        //        paint.setColor(context.resources.getColor(R.color.greyDark))

        val shader: LinearGradient = LinearGradient(
            0f,
            100f,
            width.toFloat(),
            0f,
            context.resources.getColor(R.color.colorDarkGreyGradient),
            context.resources.getColor(R.color.colorLightGreyGradient),
            Shader.TileMode.CLAMP
        )

        paint.shader = shader
        rectF.set(0f, 0f, btnWeight.toFloat(), btnHeight.toFloat())
        canvas.drawRoundRect(rectF, roundRadius.toFloat(), roundRadius.toFloat(), paint)

    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(btnWeight, btnHeight) // set desired width and height of your
    }

    fun setBarHeight(barHeight: Double) {
        btnHeight = barHeight.roundToInt() * 10

    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        btnWeight = w
        btnHeight = h
        btnRadius = btnWeight / 2
    }
}