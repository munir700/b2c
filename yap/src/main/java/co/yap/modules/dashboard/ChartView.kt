package co.yap.modules.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import co.yap.R
import kotlin.math.roundToInt

class ChartView(context: Context, attrs: AttributeSet) : View(context, attrs),
    View.OnTouchListener, View.OnFocusChangeListener {


    private var btnWeight: Int = 26
    private var btnHeight: Int = 10
    private var roundRadius: Int = 7
    private var btnRadius: Int = 0
    private var seletedColor: Int = 0
    private var isBarHighLighted: Boolean = false
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

        val fadeInBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInBarAnimation.duration = 500

        this.startAnimation(fadeInBarAnimation)

        fadeInBarAnimation.setAnimationListener(object : Animation.AnimationListener {
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
//                val fadeOutBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
//                startAnimation(fadeOutBarAnimation)
//                fadeInAnim(fadeOutBarAnimation)
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
                isBarHighLighted = true
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

    @SuppressLint("ResourceType")
    protected override fun onDraw(canvas: Canvas) {

        rectF.set(0f, 0f, btnWeight.toFloat(), btnHeight.toFloat())
        canvas.drawRoundRect(rectF, roundRadius.toFloat(), roundRadius.toFloat(), paint)
    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(btnWeight, btnHeight)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        val fadeOutBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        startAnimation(fadeOutBarAnimation)
        isBarHighLighted = false
        fadeInAnim(fadeOutBarAnimation)
    }

    @SuppressLint("ResourceAsColor")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isBarHighLighted) {
                    val fadeOutBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                    startAnimation(fadeOutBarAnimation)
                    isBarHighLighted = false
                    fadeInAnim(fadeOutBarAnimation)
                }
                customizeAnimation(context)
            }
        }

        return true
    }


    fun setBarHeight(height: Double) {
//        if (height > 1) {
        btnHeight = (btnHeight + height.roundToInt())
//        } else {
//            btnHeight = (btnHeight+ height.roundToInt())  * 10
//        }
        // need to work on fixing height
    }

    fun getHighLightedBars(): Boolean {
        return isBarHighLighted
    }

    fun unSelectHighlightedBarOnGraphClick(highlighted: Boolean) {
        val fadeOutBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        startAnimation(fadeOutBarAnimation)
        isBarHighLighted = false
        fadeInAnim(fadeOutBarAnimation)

    }


    fun unSelectHighlightedBarOnTransactionCellClick(selectBar: Boolean) {
        if (selectBar) {
            //list
            val fadeOutBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            startAnimation(fadeOutBarAnimation)
            fadeInAnim(fadeOutBarAnimation)
            isBarHighLighted = false
            customizeAnimation(context)
        } else {
            val fadeOutBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            startAnimation(fadeOutBarAnimation)
            isBarHighLighted = false
            fadeInAnim(fadeOutBarAnimation)

        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        btnWeight = w
        btnHeight = h
        btnRadius = btnWeight / 2
    }
}