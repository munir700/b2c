package co.yap.modules.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import co.yap.R
import kotlin.math.roundToInt


class ChartView(context: Context, attrs: AttributeSet) : View(context, attrs),
    View.OnTouchListener {


    private var btnWeight: Int = 50
    private var btnHeight: Int = 400
    private var roundRadius: Int = 20
    private var btnRadius: Int = 0
    private var seletedColor: Int = 0
    private var paintShader: Shader? = null
    private var paint: Paint = Paint()
    var rectF: RectF = RectF()


    init {
        paintShader = LinearGradient(
            0f,
            100f,
            width.toFloat(),
            0f,
            context.resources.getColor(R.color.colorDarkGreyGradient),
            context.resources.getColor(R.color.colorLightGreyGradient),
            Shader.TileMode.CLAMP
        )


        seletedColor = R.color.transparent
        customizePaint(context)

        this.performClick()
        setOnTouchListener(this)


    }

    private fun customizePaint(context: Context) {
        paint.color = seletedColor
        paint.setStyle(Paint.Style.FILL)
        paint.setStrokeWidth(10F)
        paint.shader = paintShader

    }

    @SuppressLint("ResourceAsColor")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {

            MotionEvent.ACTION_DOWN -> {
                paint.shader = null
                paintShader = null
                paint.color = context.resources.getColor(R.color.transparent)
                seletedColor = context.resources.getColor(R.color.colorPrimary)
                Toast.makeText(context, "ACTION_DOWN", Toast.LENGTH_SHORT).show()
                customizePaint(context)
                invalidate()

            }

            MotionEvent.ACTION_UP -> {
                Toast.makeText(context, "ACTION_UP", Toast.LENGTH_SHORT).show()
                seletedColor = R.color.transparent

                paintShader = LinearGradient(
                    0f,
                    100f,
                    width.toFloat(),
                    0f,
                    context.resources.getColor(R.color.colorDarkGreyGradient),
                    context.resources.getColor(R.color.colorLightGreyGradient),
                    Shader.TileMode.CLAMP
                )
                customizePaint(context)
                invalidate()
            }
        }
        return true
    }

    @SuppressLint("ResourceType")
    protected override fun onDraw(canvas: Canvas) {

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