package co.yap.modules.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import co.yap.R
import kotlin.math.roundToInt

class ChartView(context: Context, attrs: AttributeSet) : View(context, attrs),
    View.OnTouchListener {

    private var btnWeight: Int = 26
    private var btnHeight: Int = 50
    private var roundRadius: Int = 7
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

    private fun customizeAnimation(context: Context) {

        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeOut.duration = 1000

        this.startAnimation(fadeOut)

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                paint.shader = null
                paintShader = null
                 val pupleSelectedColor = context.resources.getColor(R.color.colorPrimary)
                paint.color = pupleSelectedColor
                seletedColor = pupleSelectedColor
                invalidate()
                customizePaint(context)
            }

            override fun onAnimationEnd(animation: Animation) {
                 val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                startAnimation(fadeIn)
                fadeInAnim(fadeIn)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    fun fadeInAnim(fadeIn: Animation) {
        fadeIn.duration = 500
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

                paintShader = LinearGradient(
                    0f,
                    100f,
                    width.toFloat(),
                    0f,
                    context.resources.getColor(R.color.colorDarkGreyGradient),
                    context.resources.getColor(R.color.colorLightGreyGradient),
                    Shader.TileMode.CLAMP
                )

                val pupleSelectedColor = context.resources.getColor(R.color.greySoft)
                seletedColor = pupleSelectedColor
                invalidate()
                customizePaint(context)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

    }

    private fun customizePaint(context: Context) {
        paint.color = seletedColor
        paint.shader = paintShader
        paint.setStyle(Paint.Style.FILL)
        paint.setStrokeWidth(10F)

    }

    @SuppressLint("ResourceAsColor")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                customizeAnimation(context)
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
        setMeasuredDimension(btnWeight, btnHeight)
    }

    fun setBarHeight(height: Double) {
        if (height > 1) {
            btnHeight = height.roundToInt()
        }else{
            btnHeight = height.roundToInt() * 10
        }
        // need to work on fixing height
    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        btnWeight = w
        btnHeight = h
        btnRadius = btnWeight / 2
    }
}